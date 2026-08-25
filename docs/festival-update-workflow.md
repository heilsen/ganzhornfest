# Yearly Festival Data Update Workflow

When preparing the app for a new Ganzhornfest edition, update these files in order.

## 1. `core-api/src/main/kotlin/de/heilsen/ganzhornfest/core/FestivalEdition.kt`

Update `year`, `editionNumber`, and `days`. This is the single source of truth for
the edition year and festival dates. All derived displays (app bar title, official
name, InfoScreen date labels) update automatically.

## 2. `info-api/src/main/res/values/strings.xml`

Update the opening hours for each day:

- `opening_hours_saturday`
- `opening_hours_sunday`
- `opening_hours_monday`

Also update `clubs_intro` if the club count changes, and `sunday_shopping` if that
notice changes.

Opening hours are editorial and not derivable from the dates.

## 3. SQLDelight seed (fresh installs)

Edit the `.sq` files. Do not patch generated artifacts.

- `Poi.sq` — clubs, stages, playgrounds, WC, first aid, bus stop
- `Offer.sq` — add newly named food/drink/other rows. Keep unused old rows unless you
  want them gone from search.
- `ClubOffer.sq` — rebuild the club-to-offer join from the flyer plus website. PDF wins
  on conflict.
- `Program.sq` — stage program rows with ISO timestamps (`2026-09-05T16:00:00+02:00`)
- `Coordinate.sq` / `PoiCoordinate.sq` — map pins. Starting guesses are fine. Refine
  with the debug pin editor.

## 4. SQLDelight migration (existing installs)

Add `database/src/main/sqldelight/migrations/<n>.sqm`. Fresh installs never re-read
`.sq` into an already created `ganzhornfest.db`. Anyone who opened last year's app
only sees new content if a migration writes it.

Keep `.sq` inserts and the new `.sqm` identical. Do not edit `1.sqm` or `2.sqm`.
Do not turn on `verifyMigrations`. The empty `databases/1.db` snapshot is intentional.

## 5. Pin editor (debug builds)

On the map, **Standorte korrigieren** (debug only):

1. Select a flyer-numbered chip.
2. Pan so the crosshair sits on the stand.
3. **Position übernehmen** writes the live DB.
4. **SQL kopieren** puts `UPDATE coordinate …` statements on the clipboard.
5. Paste those into `Coordinate.sq` and the new `.sqm`.

## 6. Version

Bump `versionCode` / `versionName` in `app/build.gradle.kts`.

## 7. Verify

```bash
./gradlew check
```

Then a debug install: search (new clubs present, dropped clubs gone), program all
three days, map vs flyer. Upgrade-install from the previous Play build to prove the
new migration.
