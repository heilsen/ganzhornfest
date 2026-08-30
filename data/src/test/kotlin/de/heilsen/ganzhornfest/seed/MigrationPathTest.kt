@file:OptIn(ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.sql.Connection
import java.util.UUID
import kotlin.time.ExperimentalTime

private data class Release(
    val name: String,
    val schemaVersion: Long,
    val fixture: String,
)

// One row per app version that has reached the Play Store. The fixture is the exact
// .sq seed shipped in that version, frozen from the git tag. See
// data/src/test/resources/migrations and docs/festival-update-workflow.md.
private val releases =
    listOf(
        Release("2024.1", 1L, "migrations/v1_2024_1.sql"),
        Release("2025.1.1", 2L, "migrations/v2_2025_1_1.sql"),
        Release("2026.1.1", 3L, "migrations/v3_2026_1_1.sql"),
    )

class MigrationPathTest :
    DescribeSpec({
        val fresh =
            onDriver { driver ->
                GanzhornfestDb.Schema.create(driver)
                driver.withConnection { it.schemaFingerprint() to it.normalizedDdl() }
            }

        releases.forEach { release ->
            it("upgrades a ${release.name} install to the current schema") {
                onDriver { driver ->
                    loadFixture(driver, release.fixture)
                    GanzhornfestDb.Schema.migrate(driver, release.schemaVersion, GanzhornfestDb.Schema.version)

                    driver.withConnection { conn ->
                        conn.schemaFingerprint() shouldBe fresh.first
                        conn.normalizedDdl() shouldBe fresh.second
                        conn.integrityCheck() shouldBe "ok"
                    }

                    val db = GanzhornfestDb(driver)
                    val (manifest, data) = parseShippedFestival()
                    seedIfNeeded(db, manifest, data)
                    db.poiQueries
                        .selectAll()
                        .executeAsList()
                        .size shouldBe data.pois.size
                }
            }
        }
    })

private fun <R> onDriver(block: (JdbcSqliteDriver) -> R): R =
    JdbcSqliteDriver("jdbc:sqlite:file:mem${UUID.randomUUID()}?mode=memory&cache=shared").use(block)

private fun <R> JdbcSqliteDriver.withConnection(block: (Connection) -> R): R {
    val (connection, close) = connectionAndClose()
    return try {
        block(connection)
    } finally {
        close()
    }
}

private fun loadFixture(
    driver: SqlDriver,
    resource: String,
) {
    val sql =
        MigrationPathTest::class.java.classLoader
            ?.getResourceAsStream(resource)
            ?.bufferedReader()
            ?.use { it.readText() }
            ?: error("Missing fixture $resource")
    sql
        .lineSequence()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .forEach { driver.execute(null, it, 0) }
}

private fun Connection.rows(sql: String): List<String> =
    createStatement().use { statement ->
        statement.executeQuery(sql).use { result ->
            val columns = result.metaData.columnCount
            buildList {
                while (result.next()) {
                    add((1..columns).joinToString("|") { result.getString(it) ?: "" })
                }
            }
        }
    }

// Built from pragma_* rather than sqlite_master.sql. Migration 1 renames tables into
// place with ALTER TABLE ... RENAME, after which SQLite stores the name quoted, so a
// text diff of the DDL trips on `"busLine"` vs `busLine` while the tables are identical.
private fun Connection.schemaFingerprint(): String =
    buildString {
        for (table in rows("SELECT name FROM sqlite_master WHERE type = 'table' AND name NOT LIKE 'sqlite_%' ORDER BY name")) {
            appendLine("TABLE $table")
            rows(
                "SELECT cid, name, type, \"notnull\", ifnull(dflt_value, ''), pk " +
                    "FROM pragma_table_info('$table') ORDER BY cid",
            ).forEach { appendLine("  COL $it") }
            for (index in rows("SELECT name, \"unique\", origin, partial FROM pragma_index_list('$table') ORDER BY name")) {
                appendLine("  INDEX $index")
                rows("SELECT seqno, cid, ifnull(name, '') FROM pragma_index_info('${index.substringBefore('|')}') ORDER BY seqno")
                    .forEach { appendLine("    ON $it") }
            }
        }
    }

// Catches what pragma_table_info cannot see, notably AUTOINCREMENT. Whitespace and
// quotes are stripped so formatting differences between the .sq DDL and the DDL a
// migration produces do not register.
private fun Connection.normalizedDdl(): String =
    rows("SELECT type, name, ifnull(sql, '') FROM sqlite_master WHERE name NOT LIKE 'sqlite_%' ORDER BY type, name")
        .joinToString("\n") { it.replace("\"", "").replace(Regex("\\s+"), "") }

private fun Connection.integrityCheck(): String = rows("PRAGMA integrity_check").first()
