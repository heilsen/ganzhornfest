# P3 · Program screen as a timetable

## Goal

Replace the single-stage dropdown view with a day-tab timetable that shows all stages at once,
sorted by time with sticky time-of-day headers, so a visitor can answer "what's on right now"
without flipping between stages one at a time.

## Why now

`program/src/main/kotlin/de/heilsen/ganzhornfest/program/ProgramScreen.kt` and
`ProgramPresenter.kt` currently force a single-stage view: `ProgramPresenter.kt:35` fetches
`locations` via `getStages()`, defaults `selectedLocation` to the first stage
(`ProgramPresenter.kt:37-39`), and every render shows only that one stage's events
(`ProgramScreen.kt:66` `SelectionConfig` for `selectedLocation`). `docs/TODO.md` explicitly asks
to "rework the `ProgramScreen` into a timetable-style layout."

The query that backs this, `Program.sq:getPrograms`, already supports an all-stages fetch: the
`WHERE stage LIKE :stage` clause (`Program.sq:9`) matches everything when `:stage` is `'%'`.
**No `.sq` change needed** — this keeps the plan off files your `feat/festival-data-2026`
branch and `feat/json-seed` are both touching.

One important caveat for whoever implements this: the `program` table is currently **empty**.
`migrations/2.sqm:1-3` explicitly drops and recreates it with a comment "Clear 2025 program;
2026 lineup to be added later." Build and verify this against `PreviewParameterProvider`-backed
fixture data (follow the pattern in `bus-impl/src/main/kotlin/de/heilsen/ganzhornfest/bus/preview/BusModelPreviewParameterProvider.kt`),
not against the live (empty) DB, and make sure the empty state renders sensibly since it's the
literal current production state.

## Worktree

```bash
/start-implement feat program-timetable
```

## Files owned

- `program/src/main/kotlin/de/heilsen/ganzhornfest/program/**` (all of it)
- `program/src/main/res/**` if any new strings are needed (currently no `strings.xml` exists in
  this module — check whether one needs adding, following the pattern in
  `feature/search-impl/src/main/res/values/strings.xml`)
- New `program/src/test/kotlin/de/heilsen/ganzhornfest/program/**` and a new
  `ProgramPreviewParameterProvider.kt`

Fully isolated module. No overlap with any other plan or in-flight branch — nothing outside
`:program` touches the program feature.

## Steps

1. **Change the query call, not the query.** In `ProgramPresenter.present()`
   (`ProgramPresenter.kt:52`), when the UI is in "all stages" mode, call
   `getPrograms(location = "%", start, end)` instead of a specific stage name. Confirm
   `GetProgramsUseCase.invoke` (`GetProgramsUseCase.kt:15`) doesn't short-circuit on `"%"` — it
   currently only short-circuits on `location == null` (`GetProgramsUseCase.kt:19`), so `"%"`
   passes through fine.
   *Verify:* a unit test on `GetProgramsUseCase` (new, since none exists today) asserting
   `invoke(null, ...)` returns empty and `invoke("%", ...)` delegates to the repository.

2. **Day tabs.** Replace the `SelectionCard`'s date dropdown (`ProgramScreen.kt:69-74`) with a
   horizontal row of tabs, one per `FestivalEdition.days` entry (via `getOpeningDays()`, same
   source `ProgramPresenter` already uses at line 32). Use Material3 `TabRow`/`Tab` or a
   `ScrollableTabRow` if three days risk overflow on small screens — three days should fit
   without scrolling, but check against `PreviewScreenSizes` (already used elsewhere via
   `core-api/.../compose/preview/PreviewDefault.kt`).
   *Verify:* preview screenshots across the screen sizes covered by `@PreviewDefault`.

3. **Stage filter becomes optional, not primary.** Keep a way to filter to one stage (useful if
   the lineup is large), but make "all stages" the default `selectedLocation` state instead of
   `getStages().first()` (current default at `ProgramPresenter.kt:38`). This likely means
   changing `selectedLocation: String?` semantics so `null` means "all stages" — trace every
   call site of `selectedLocation` in `ProgramScreen.kt` and `ProgramPresenter.kt` since `null`
   currently means "not yet loaded," not "show everything." You'll need a distinct sentinel or a
   small sealed type (`Location.All` / `Location.Named(String)`) to avoid conflating those two
   meanings — this is the trickiest part of the plan, take care here.
   *Verify:* a presenter test asserting the initial state is "all stages" without waiting for
   `getStages()` to resolve.

4. **Sticky time headers.** In the `Programs` composable (`ProgramScreen.kt:83`), group
   `programs` by hour-of-day (or by day-part: morning/afternoon/evening, whichever reads better
   given real festival hours — check `FestivalOpeningDays`/`FestivalEdition` for what "open"
   actually spans) and use `LazyColumn`'s `stickyHeader { }` per group, same mechanism
   `DetailScreen.kt:65` already uses for its section title. Keep the existing `Ticket` component
   (`theme/component/ticket/Ticket.kt`) for each program entry — do not redesign the ticket
   itself, only the grouping around it.
   *Verify:* preview with fixture data spanning multiple hours renders visible sticky headers
   that don't overlap ticket content.

5. **Empty state.** `Programs` already handles empty via `theme/component/EmptyScreen`
   (`ProgramScreen.kt:85-90|EmptyScreen`). Since the table is genuinely empty in production right
   now, make sure this state's copy doesn't read as broken — "Programm wird noch bekannt
   gegeben" (or similar) rather than the current "Bitte die Auswahl oben ändern," which implies
   user error when there may simply be no data yet.
   *Verify:* manual check against the actual (empty) `program` table via `./gradlew :app:installDebug`.

## Tests

`program/build.gradle.kts` currently has **no** test dependencies
(`kotest`/`mockk`/`turbine` absent). Add them, matching the versions in `libs.versions.toml`
and the dependency block style in `feature/search-impl/build.gradle.kts`. Add:
- `GetProgramsUseCaseTest` (new) covering the `"%"` all-stages path.
- `ProgramPresenterTest` (new) using `turbine`, covering: initial "all stages" default, day tab
  switching, single-stage filter still working.
- A `ProgramPreviewParameterProvider` (new, in a `preview` package matching
  `bus-impl/.../preview/`) with fixture data spanning multiple stages and times, since the live
  DB has none.

## Done when

`./gradlew check` passes, then `/create-pr`.
