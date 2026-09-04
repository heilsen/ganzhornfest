# P10 · Unblock the JSON seed, then harden the schema — Wave 2, blocked

## ⚠ Blocked

`feat/json-seed` and `feat/festival-data-2026` both currently add
`database/src/main/sqldelight/migrations/3.sqm` as an untracked new file in their respective
worktrees — they will conflict with each other the moment either merges, before this plan even
starts. This plan's first two steps are about resolving that landmine, not about the schema
hardening work itself. **Do not start the hardening steps (4+) until both branches have merged
and the migration numbering conflict is actually resolved on `main`.** Steps 1–2 below can and
should happen immediately, independent of which branch merges first — they're gitignore/hygiene
fixes, not schema changes.

## Goal

First: stop `.gitignore` from silently swallowing the JSON seed assets, and flag the
duplicate-migration-number collision before it causes a merge conflict or silent data loss.
Second, once the dust settles: turn on SQLDelight's migration verification, add indexes for the
`LIKE` scans search already runs, add missing foreign keys, and wire up the `countClubs` query
that already exists but is unused.

## Why now

**The gitignore bug.** `.gitignore:12` reads:
```
assets/
```
with no leading slash. Git glob semantics: a pattern with no `/` in the middle (or only a
trailing one) matches at **any depth** in the tree, not just the repo root. Confirmed directly —
`git -C ../GanzhornfestCompose-json-seed status --short` shows:
```
?? app/src/main/assets/
```
as untracked, meaning every file already written into `app/src/main/assets/` in that worktree
(the actual JSON seed payload, per that branch's name and its new
`data/src/main/kotlin/de/heilsen/ganzhornfest/seed/` package) is currently invisible to git and
would never make it into a commit or PR. This is silent — `git add -A` and even `git add .`
would say nothing, they'd simply not add those files, and nobody would notice until the seed
data mysteriously isn't in the built APK.

**The migration collision.** Both `git -C ../GanzhornfestCompose-json-seed status --short` and
`git -C ../GanzhornfestCompose-festival-data-2026 status --short` show
`?? database/src/main/sqldelight/migrations/3.sqm` as a new untracked file. SQLDelight migration
files are numbered sequentially and applied in order — two different `3.sqm` files with
different content is a straightforward collision the moment either branch is rebased onto the
other's merge, and worse, if merged without noticing, whichever `3.sqm` lands second **silently
overwrites** the first at that path with no merge conflict marker triggered by SQLDelight itself
(only by git, and only if git's own three-way merge doesn't auto-resolve it, which for two
distinct new files at the same path it usually will *not* flag as a conflict if merged via
separate PRs sequentially rather than merged into each other directly — the second PR's `3.sqm`
addition just overwrites the first's silently since it's a "new file" from that PR's perspective
against its own base).

**Missing migration verification.** `database/build.gradle.kts`'s `sqldelight { databases {
create("GanzhornfestDb") { ... } } }` block (lines 23-30) does not set `verifyMigrations = true`.
SQLDelight's migration verification compares the schema produced by replaying all `.sqm` files
against the checked-in snapshot (`database/src/main/sqldelight/databases/1.db`, currently 0
bytes per `wc -l` — actually binary, size not line count, but confirmed present) — without it
turned on, a migration that doesn't actually produce the schema the `.sq` files expect fails
silently at runtime on a user's device instead of at build time.

**Missing indexes.** Every name-search query in the app runs an unindexed `LIKE '%term%'` scan:
`Offer.sq`'s `selectByName`/`selectFoodByName`/`selectDrinkByName`/`selectOthersByName`
(lines 238-255), `Poi.sq`'s `selectClubByName` (last line). Leading-wildcard `LIKE '%x%'`
patterns can't use a simple B-tree index for the match itself, but SQLite's FTS5 extension or,
at minimum, a plain index on `name`/`description` still helps the `typeId` pre-filter that
precedes the `LIKE` in most of these queries. Given the dataset is small (tens to low hundreds
of rows per `Poi.sq`'s seed data), this is a minor perf item, not urgent — include it for
completeness since `docs/TODO.md` asks for it, but don't over-invest.

**Missing foreign keys.** `ClubOffer.sq` (274 lines, mostly seed `INSERT`s) joins `clubOffer` to
both `poi` and `offer` (`ClubOffer.sq:266-267` in `selectOffersByClubName`) with no declared
`FOREIGN KEY` constraint on `clubOffer.poiId`/`clubOffer.offerId` in the `CREATE TABLE`.
Similarly `Program.sq`'s `program.poiId` and `PoiCoordinate.sq`'s join column. Without FKs,
`PRAGMA foreign_keys = ON` (if ever enabled) does nothing, and there's no DB-level protection
against inserting a `clubOffer` row pointing at a deleted/nonexistent `poi`/`offer` id.

**Unused `countClubs`.** `Poi.sq`'s last query block:
```sql
countClubs:
SELECT count(*) FROM poi WHERE typeId = 1;
```
already exists and is presumably generated into `PoiQueries.countClubs()` by SQLDelight, but
`info-api/src/main/kotlin/de/heilsen/ganzhornfest/info/InfoScreen.kt:173` hardcodes:
```kotlin
append("39 Neckarsulmer Vereine bieten:")
```
a manually-counted, manually-maintained literal that will silently go stale the next time a club
is added or removed — which is exactly the kind of yearly-update-workflow risk
`docs/festival-update-workflow.md` is trying to eliminate for other fields.

## Files owned

- `.gitignore`
- `database/src/main/sqldelight/migrations/3.sqm` (renumbering coordination only — the actual
  content is owned by whichever of `feat/json-seed`/`feat/festival-data-2026` wrote it first)
- `database/build.gradle.kts`
- `database/src/main/sqldelight/de/heilsen/ganzhornfest/database/*.sq` (adding indexes/FKs)
- New `database/src/test/` migration test module (check whether `:database` has any test
  source set at all today — `database/build.gradle.kts` currently declares no test
  dependencies, this may be the first test in this module)
- `info-api/src/main/kotlin/de/heilsen/ganzhornfest/info/InfoScreen.kt` (only the
  `countClubs` wiring, line ~173) — **check first** whether `feat/festival-data-2026` has
  already rewritten this exact area (its diff shows `InfoScreen.kt` with +87/-‑ lines changed);
  if so, rebase this specific change onto whatever that branch leaves behind rather than
  reverting its work.

## Steps

1. **Fix `.gitignore` immediately, independent of anything else.** Change line 12 from
   `assets/` to `/assets/` (anchors it to repo root only, where it was presumably intended to
   exclude a top-level scratch/build assets dir — confirm nothing at the actual repo root
   currently relies on the unanchored form before narrowing it; `git ls-files | grep assets`
   shows the only pre-existing tracked `assets/` content is `assets/screenshots/` and
   `assets/launcher-icon.*` at the repo root, which `/assets/` still correctly ignores... wait,
   those ARE tracked already per `git ls-files`, meaning they're tracked despite the ignore rule
   — git doesn't retroactively untrack already-tracked files just because a rule matches them
   later, so this is safe either way). Push this as a tiny standalone commit/PR immediately, it
   unblocks `feat/json-seed` regardless of merge order with anything else in this batch.
   *Verify:* `cd ../GanzhornfestCompose-json-seed && git status --short` — after pulling this
   fix into that worktree, `app/src/main/assets/` should now show as trackable (still `??` until
   `git add`, but no longer masked — confirm with `git check-ignore -v app/src/main/assets/some-file.json`
   returning nothing).

2. **Flag, don't silently resolve, the migration collision.** Do not pick a winner yourself —
   post/note (in the PR description of whichever of `feat/json-seed` /
   `feat/festival-data-2026` merges second) that its `3.sqm` needs renumbering to `4.sqm` before
   merge, and that its `sqldelight { databases { ... } }` migration ordering assumption should
   be re-verified against whatever `3.sqm` the first-merged branch actually shipped. This is a
   coordination step for you, not something an agent should resolve unilaterally without knowing
   which branch's migration content is meant to be authoritative.

3. **Wait for both to land**, then re-derive the actual final migration file list on `main`
   before touching anything in steps 4+.

4. **Turn on migration verification.** Add `verifyMigrations = true` inside the
   `create("GanzhornfestDb") { }` block in `database/build.gradle.kts`. Run
   `./gradlew :database:verifySqlDelightMigration` (task name may differ slightly by SQLDelight
   2.3.2's actual task naming — check `./gradlew :database:tasks --group sqldelight` if unsure)
   and fix whatever it flags. Confirm `databases/1.db` stays exactly as-is — per your own
   standing constraint, this snapshot must remain data-free; migrations are what seed data, not
   the snapshot itself. If verification reveals the snapshot needs regenerating, regenerate it
   via the SQLDelight Gradle task specifically, never by hand-editing the `.db` file, and confirm
   with `sqlite3 database/src/main/sqldelight/databases/1.db "SELECT count(*) FROM poi;"` (and
   similar for other tables) that it returns 0 rows.
   *Verify:* `./gradlew check` includes this verification and fails loudly if a future migration
   drifts from the `.sq` schema.

5. **Add migration tests.** SQLDelight supports testing migrations by applying them against an
   in-memory driver and asserting the resulting schema/data matches expectations. Add a test
   module/source set to `:database` (none exists today) exercising at minimum: applying all
   migrations from `1.db` in order produces a schema matching current `.sq` files, and that the
   known seed-data mutations in `1.sqm`/`2.sqm` (e.g. the POI deletes/inserts at
   `1.sqm` lines 26-32) apply cleanly.
   *Verify:* new test passes; deliberately break a migration temporarily to confirm the test
   actually catches it before reverting.

6. **Add indexes.** `CREATE INDEX idx_offer_name ON offer(name)` and similar for
   `offer.description`, `poi.name` — add these as a new migration (`.sqm`) rather than editing
   the base `.sq` `CREATE TABLE` statements directly, since the schema has already shipped and
   changes need to go through the migration path like everything else in this database.
   *Verify:* `EXPLAIN QUERY PLAN SELECT * FROM offer WHERE typeId = 2 AND name LIKE '%x%'`
   (run via `sqlite3` against a built `.db`) shows the index being used for the `typeId`
   pre-filter.

7. **Add foreign keys.** Also via a new migration: recreate `clubOffer`, `program`,
   `poiCoordinate` with explicit `FOREIGN KEY (...) REFERENCES ...` clauses, following the same
   drop-recreate-reinsert pattern already used in `migrations/1.sqm` (e.g. lines 1-4's
   `coordinate_autoincrement` rename dance) since SQLite's `ALTER TABLE` can't add a `FOREIGN
   KEY` to an existing table directly.
   *Verify:* migration test from step 5 confirms the recreated tables retain all existing rows
   with no data loss, and that `PRAGMA foreign_key_check` reports no violations against current
   seed data (i.e. confirm there are no orphaned `clubOffer`/`program` rows already in the seed
   data before the constraint would start rejecting new ones).

8. **Wire up `countClubs`.** In `InfoScreen.kt`, replace the hardcoded "39 Neckarsulmer Vereine"
   with a value read from `PoiRepository`/a new small `GetClubCountUseCase` wrapping
   `poiQueries.countClubs()`. Check `PoiRepository.kt` — it currently exposes `getAll()`,
   `selectByName()`, `getStages()` but not `countClubs()`; add a thin wrapper method following
   its existing style. `docs/festival-update-workflow.md` should be updated to remove any
   implicit expectation that this count needs manual yearly updating (it currently doesn't
   mention it at all, so this is just making an implicit dependency explicit and then
   eliminating it).
   *Verify:* a unit test asserting the rendered string reflects the actual row count against a
   test fixture DB, not a hardcoded expectation of 39.

## Tests

`:database` has no test infrastructure today — step 5 is the first. `PoiRepository`/`InfoScreen`
tests for step 8 go wherever `:info-api`'s test setup ends up (also likely nonexistent yet —
check `info-api/build.gradle.kts` first).

## Done when

Steps 1–2 land as their own quick PR immediately (don't wait for the rest of this plan). Once
both blocking branches merge and steps 4–8 are done, `./gradlew check` passes including the new
migration verification task, then `/create-pr`.
