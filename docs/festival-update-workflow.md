# Yearly Festival Data Update Workflow

When preparing the app for a new Ganzhornfest edition, update these files in order.

## 1. `core-api/src/main/kotlin/de/heilsen/ganzhornfest/core/FestivalEdition.kt`

Update `year`, `editionNumber`, and `days`. This is the single source of truth for
the edition year and festival dates. All derived displays (app bar title, official
name, InfoScreen date labels) update automatically.

If the number of days changes, add or remove a matching `opening_hours_*` string in
step 2. Info's date chip row zips `days` against that string list, so a mismatch
silently drops chips instead of crashing. `FestivalEditionTest` in `core-api` asserts
the day count as a tripwire for this.

## 2. `info-api/src/main/res/values/strings.xml`

Update the opening hours for each day:

- `opening_hours_saturday`
- `opening_hours_sunday`
- `opening_hours_monday`

Also update `sunday_shopping` if that notice changes. Club count on Info is
`count(*)` of POIs with type club.

Opening hours are editorial and not derivable from the dates.

## 3. JSON seed (all installs)

Edit the assets in `app/src/main/assets/festival/`.

- `manifest.json`: `year`, `timezone`, and `dataVersion`. Bump `dataVersion` whenever
  content changes.
- `data.json`: `poiTypes`, `offerTypes`, `coordinates`, `pois`, `poiCoordinates`,
  `offers`, `offerAliases`, `clubOffers`, `busLines`, `busConnections`, `programs`.

If two offers are the same item, fold the extra name into `offerAliases` instead of
keeping a duplicate offer row. Search inner-joins `clubOffer`, so an offer nobody
sells is invisible regardless of aliases.

Times in JSON are year-less, like `"09-05T16:00"`. The seeder fills year and timezone
from the manifest.

On app start the seeder compares `dataVersion` to `seedMeta`. If the asset is newer,
it wipes content tables and reloads JSON. Fresh installs and upgrades both go through
this path. Do not put content `INSERT`s in `.sq` or `.sqm` files.

## 4. Schema-only changes

Schema still lives in the `.sq` files. Add a new
`database/src/main/sqldelight/migrations/<n>.sqm` when the schema changes. Do not edit
`1.sqm`, `2.sqm`, `3.sqm`, or `4.sqm`. Do not turn on `verifyMigrations`. The empty
`databases/1.db` snapshot is intentional. `5.sqm` creates `seedMeta`.

## 5. Pin editor (debug builds)

On the map, **Standorte korrigieren** (debug only):

1. Select a flyer-numbered chip.
2. Pan so the crosshair sits on the stand.
3. **Position übernehmen** writes the live DB.
4. **SQL kopieren** puts `UPDATE coordinate …` statements on the clipboard.
5. Copy those lat/lng values into `data.json` `coordinates` (and `poiCoordinates` if a
   pin is new). Bump `dataVersion`.

## 6. Version

Bump `versionCode` / `versionName` in `app/build.gradle.kts`.

## 7. Verify

```bash
./gradlew check
```

Then a debug install: search (new clubs present, dropped clubs gone), program all
three days, map vs flyer. Upgrade-install from the previous Play build to prove the
seeder reloads after the `seedMeta` migration.
