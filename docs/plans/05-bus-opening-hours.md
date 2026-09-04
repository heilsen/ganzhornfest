# P5 · Real festival opening hours, and an honest "no trips" state

## Goal

Stop hardcoding the bus search window to "19:00 to 03:00 the next day" and its matching label,
drive both from real per-day opening hours, and stop telling users "change your selection" when
the actual problem is that the bus operator hasn't published trips yet for that
destination/day.

## Why now

`bus-impl/src/main/kotlin/de/heilsen/ganzhornfest/bus/BusPresenter.kt:45-46`:
```kotlin
val start = departureDate.atTime(19, 0)
val end = departureDate.plus(1, DateTimeUnit.DAY).atTime(3, 0)
```
This is a fixed window regardless of the actual day's opening/closing hours. Saturday, Sunday,
and Monday of the festival plausibly have different hours (see
`info-api/src/main/res/values/strings.xml`'s `opening_hours_saturday/sunday/monday` strings,
which are separate editorial text per day already) — the bus window should track them, not be a
constant.

`bus-impl/src/main/kotlin/de/heilsen/ganzhornfest/bus/BusScreen.kt:94`:
```kotlin
Text("Verbindungen (ab 19 Uhr)")
```
Hardcoded label, independent of the actual query window above — if one changes, the other
silently goes stale. `docs/TODO.md` names both of these exactly: "Stop hardcoding the bus start
time to `19:00`" and "Introduce opening hours per day and use them for bus/program filtering."

Separately, `bus-impl/src/main/kotlin/de/heilsen/ganzhornfest/bus/BusScreen.kt:106-112`:
```kotlin
if (connections.isEmpty()) {
    EmptyScreen { Text("Bitte die Auswahl oben ändern", ...) }
}
```
This is shown identically whether the user picked an unusual destination/date combo that
genuinely has no service, *or* whether the HNV/city simply hasn't published extra trips for that
destination yet (a known real-world situation per your day job updating this data). Telling the
user to "change the selection above" when the selection is fine is actively misleading.

## Worktree

```bash
/start-implement feat bus-opening-hours
```

## Files owned

- New `core-api/src/main/kotlin/de/heilsen/ganzhornfest/core/FestivalHours.kt` — deliberately a
  **new** file, not `core-api/.../FestivalEdition.kt`, since that file is the one you edit each
  year for dates and is not part of this plan's footprint.
- `core-impl/src/main/kotlin/de/heilsen/ganzhornfest/core/` — a new use case or provider backing
  `FestivalHours`, following the existing `FestivalOpeningDays`/`SelectDefaultDateUseCase`
  pattern (interface in `core-api`, `@ContributesBinding` impl in `core-impl`).
- `bus-api/src/main/kotlin/de/heilsen/ganzhornfest/bus/**` — `BusModel.kt` gains a distinction
  between "empty because unpublished" and "empty because no match."
- `bus-impl/src/main/kotlin/de/heilsen/ganzhornfest/bus/BusPresenter.kt`,
  `BusScreen.kt`, `GetBusConnectionsUseCase.kt`, `BusConnectionRepository.kt`
- `bus-api/src/main/res/values/strings.xml`, `bus-impl` strings if any exist
- `database/src/main/sqldelight/de/heilsen/ganzhornfest/database/BusLine.sq` — a new named
  query only (`SELECT DISTINCT destination`), no `CREATE TABLE` or column change, so no
  migration.

**Do not touch:** `core-api/.../FestivalEdition.kt` (you're actively editing this for 2026
dates), anything under `:map`, `:program`, other `.sq` files. The only schema-file edit is the
read-only query added to `BusLine.sq` in step 5.

## Steps

1. **Define `FestivalHours`.** In `core-api`:
   ```kotlin
   data class DayHours(val date: LocalDate, val opensAt: LocalTime, val closesAt: LocalTime)
   fun interface GetFestivalHoursUseCase {
       operator fun invoke(): PersistentList<DayHours>
   }
   ```
   Implement in `core-impl`, sourcing dates from the existing `FestivalEdition.days` /
   `GetOpeningDaysUseCase` and hardcoding hours as editorial constants next to it (same pattern
   as the `opening_hours_*` strings already being hand-maintained in
   `info-api/.../strings.xml` — this is genuinely editorial data, not derivable, per
   `docs/festival-update-workflow.md`'s own note that "Opening hours are editorial and not
   derivable from the dates"). Update `docs/festival-update-workflow.md` to mention this new
   file needs the same yearly review as `FestivalEdition.kt`.
   *Verify:* unit test asserting three `DayHours` entries matching `FestivalEdition.days`.

2. **Drive the bus query window from it.** In `BusPresenter.present()`, replace the hardcoded
   `atTime(19, 0)` / next-day `atTime(3, 0)` with the `DayHours` entry matching
   `departureDate`, plus a fixed buffer past closing for last-bus-of-the-night trips (the
   existing window already extends past midnight to `03:00`, so keep an explicit "trips continue
   until N hours after closing" constant rather than assuming closing time = last bus).
   *Verify:* a presenter test with a `DayHours` fixture asserting the query start/end match the
   configured hours, not a literal `19`/`3`.

3. **Drive the label from the same source.** `BusScreen.kt:94`'s
   `Text("Verbindungen (ab 19 Uhr)")` should read from `busModel`'s opening time for the
   selected day (add it to `BusModel.Data` if not already derivable) formatted via the existing
   `formatToLocalTime` helper (`core-api/.../datetime/LocalDateFormatter.kt`), not a literal
   string. Move the German text to `bus-api/src/main/res/values/strings.xml` as a formatted
   string resource (`"Verbindungen (ab %1$s Uhr)"`), matching the existing pattern of
   `bus_screen_title` living in `bus-api`'s strings.
   *Verify:* changing the fixture `DayHours` opening time changes the rendered label in a
   preview.

   **Locale caveat.** `formatToLocalDate`, `formatToLocalTime`, `formatToLocalDateTime` and
   `dayOfTheWeek` in `core-api/.../datetime/LocalDateFormatter.kt` call `java.text.DateFormat` /
   `SimpleDateFormat` with no `Locale`, so they render in `Locale.getDefault()`, the device
   locale, not the app's German UI locale. `androidResources.localeFilters += setOf("de")`
   (`app/build.gradle.kts:29`) does not change that. Only `formatToLocalWeekdayDate`
   (`LocalDateFormatter.kt:79`) pins `Locale.GERMANY`. On a non-German device the Programm "Tag"
   dropdown (`ProgramScreen.kt:78`), the ticket times (`:130`, `:135`) and any bus time this
   step adds come out in the device language. Pin `Locale.GERMAN` in these helpers, or thread
   `ConfigurationProvider.getLocale()` through, as part of this step or the P11 sweep. Tracked in
   `docs/TODO.md`.

4. **Split the empty states.** Widen `BusModel.Data` (or add a sibling state) to distinguish:
   - **No trips published yet** for this destination — genuinely no `busConnection` rows exist
     for that `busLineId` within the whole festival window, regardless of date filter. This
     needs a new query or a check against `GetBusConnectionsUseCase` called with the full
     festival window rather than just the selected day, or simpler: track it as a known
     editorial flag per destination.
   - **No trips match this specific date** — trips exist elsewhere in the festival for this
     destination, just not on the selected day.
   Update `BusScreen.kt`'s `Connections` composable (line 101-127) to render distinct
   `EmptyScreen` copy for each, e.g. "Für dieses Ziel sind noch keine Sonderfahrten
   veröffentlicht" vs. "Keine Fahrten für den gewählten Tag."
   *Verify:* presenter test with a destination that has zero total rows vs. one with rows on
   other days only, asserting the model carries which case applies.

   Verified against the current seed (`app/src/main/assets/festival/data.json`): `busLine` has
   15 rows, 8 of which (ids 1, 4, 5, 7, 8, 9, 10, 12) have zero `busConnection` rows. So the
   "unpublished" case is real and present in shipping data, not hypothetical.

5. **Fix the destination list.** `BusPresenter.kt:33` hardcodes
   `persistentListOf("Amorbach", "Dahenfeld", "Neuberg", "Obereisesheim")`. The seed has **five**
   distinct `busLine.destination` values. **Plattenwald is missing.** Its line (seed id 3,
   `destination = 'Plattenwald'`, `stops = 'Amorbach'`) carries **14 `busConnection` rows**, the
   single largest block in the 44-row table, spread across all three festival days. A user cannot
   select "Plattenwald" from the dropdown at all. Those trips surface only by accident: picking
   "Amorbach" matches line 3 through the query's `busLine.stops LIKE :destination` clause
   (`BusConnection.sq:11-15`) and shows them as "Richtung Plattenwald".

   The earlier assumption in this plan that the `"Amorbach"` default lands on an empty state is
   **wrong**. On Saturday and Sunday, `"Amorbach"` still matches lines 2 and 3 via `stops`, so
   the tab opens on real results. Only line 11 (`destination = 'Amorbach'`) is Monday-only.

   Replace the literal with a `SELECT DISTINCT destination FROM busLine ORDER BY destination`
   query (add it to `BusLine.sq`, expose through `BusConnectionRepository`). Default
   `destination` to the first entry of that list rather than a hardcoded string.
   *Verify:* presenter test asserting the destination list equals the distinct seed destinations
   including "Plattenwald", and that the default is the first entry.

## Tests

`bus-impl/build.gradle.kts` — check for existing test deps first. Add/extend:
- `core-impl`: unit test for the new `GetFestivalHoursUseCase` binding.
- `bus-impl`: extend `BusPresenter` coverage (add if none exists — check
  `bus-impl/src/test/` first) for the window-derivation and dual-empty-state logic above.

## Done when

`./gradlew check` passes, then `/create-pr`.
