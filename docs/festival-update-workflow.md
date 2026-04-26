# Yearly Festival Data Update Workflow

When preparing the app for a new Ganzhornfest edition, update these files in order:

## 1. `core-api/src/main/kotlin/de/heilsen/ganzhornfest/core/FestivalEdition.kt`

Update `year`, `editionNumber`, and `days`. This is the single source of truth for
the edition year and festival dates. All derived displays (app bar title, official
name, InfoScreen date labels) update automatically.

## 2. `info-api/src/main/res/values/strings.xml`

Update the opening hours for each day:
- `opening_hours_saturday`
- `opening_hours_sunday`
- `opening_hours_monday`

Opening hours are editorial and not derivable from the dates.

## 3. Verify

```bash
./gradlew :app:assembleDebug
./gradlew test
```
