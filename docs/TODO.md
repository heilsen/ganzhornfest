# TODO

## High Priority

- [ ] Fix tablet-adaption
  - The app has no `NavigationRail` or `ListDetailPaneScaffold`, so Programm, Info and Bus keep the bottom
  `NavigationBar` and a single column at any width. They read as a stretched phone.
   `docs/plans/12-tablet-adaptive-layout.md` tracks the fix.

- [ ] Fix detail/navigation architecture
  - [x] Replace title-based detail routing with stable IDs
  - Scope screen state to the `NavBackStackEntry` instead of using app-wide shared view models
  - Remove the `DetailEvent` push from `MainScreen` before navigation
  - Add deep-link and back stack restoration support once routes are ID-based

- [x] Fill in missing loading and error states
  - [x] Show a proper loading state in `MapScreen`
  - [x] Show a proper loading state in `DetailScreen`
  - [x] Add a reusable error state for failed DB/data loads instead of blank screens

- [ ] Use real festival opening times in time-based features
  - Stop hardcoding the bus start time to `19:00`
  - Introduce opening hours per day and use them for bus/program filtering
  - Derive the bus destination list from `busLine`, not the 4-entry literal in `BusPresenter`.
    The seed has 5 destinations. `Plattenwald` is absent and its 14 departures, the largest
    block in the table, are only reachable by chance through the `Amorbach` filter. Tracked in
    `docs/plans/05-bus-opening-hours.md` step 5.

## UX And Feature Work

- [ ] Improve detail and map flows
  - Finish detail-to-detail navigation from related items using IDs
  - [x] Replace the temporary placeholder in `DetailScreen` map interactions (detail is a sheet on the live map)

- [ ] Polish map presentation and performance
  - Revisit the default `HYBRID` map type for the main festival map
  - Cache/precompute `MarkerUi.icon` instead of creating a `BitmapDescriptor` on every access
  - Delete `MapModel.isFullscreen`
    - It defaults to true and is never set false. The camera padding branches that read it
      were collapsed into `SEARCH_BAR_CAMERA_INSET` plus the status bar inset, so the field
      is now unread.
  - [x] Keep the marker legend colors aligned with the actual marker colors (labels are
    still hardcoded German strings, not resources, tracked below)

- [ ] Finish Info and Program screen polish
  - Replace hardcoded counts/text in `InfoScreen` with DB-backed values and string resources
  - [x] Remove or fill the empty `Card` in `InfoScreen`
  - Rework the `ProgramScreen` into a timetable-style layout

- [ ] Consider small product additions
  - [x] Instagram deep link
  - In-app review prompt

- [ ] Adapt navigation and layout for tablets and landscape
  - The bottom `NavigationBar` stays pinned at any width. Programm, Info and Bus render as a
    stretched single column on tablets. The 2026 tablet captures show it. Those files are local
    only. See `docs/playstore-capture.md`.
  - `NavigationSuiteScaffold` for a rail at medium width and up, plus a content width cap on
    the single-column screens. Tracked in `docs/plans/12-tablet-adaptive-layout.md`.

## Architecture And Data

- [ ] Move navigation and state handling out of composables where it is currently side-effect driven
  - Remove the `MainScreen` navigation TODO by emitting navigation effects instead of navigating inline
  - Keep presenters/view models predictable and easier to test

- [ ] Move ViewModels off the app graph
  - Provide them through `viewModel()` with a Metro-backed factory instead of reading them from `EntryPoint` in composition
  - Unblocks scoping screen state to the `NavBackStackEntry`
  - `remember` at the read site in `MainScreen` is the interim workaround

- [ ] Reduce lifecycle-sensitive presenter state
  - Revisit presenter-local `mutableStateOf` usage in `BusPresenter`, `ProgramPresenter`, and `SearchPresenter`
  - Keep presenter state derivation consistent across features

- [ ] Tighten database schema integrity and query performance
  - Add explicit foreign keys where missing
  - Add indexes for frequent lookups used by search and other list/detail flows
  - [x] Add migration tests for SQLDelight (`MigrationPathTest`, one fixture per shipped release)

## Quality, Tooling, And Release

- [ ] Expand automated tests
  - Add presenter/view model tests for bus, program, search, map, and detail flows
  - Add DB integration tests (migration path tests done, see `MigrationPathTest`)
  - Add UI tests for navigation paths such as Search -> Detail and Map -> Detail

- [ ] Improve build and repo hygiene
  - Stop reading `local.properties` during Gradle configuration

- [ ] Modernize Android setup
  - Add edge-to-edge/insets handling
  - Add a `<monochrome>` layer to the adaptive icon. There is none, so Android 13+ themed icons
    fall back to the full-colour badge.

- [ ] Improve release readiness and privacy
  - Standardize logging and avoid sensitive logs in release builds
  - Add a licences screen for bundled OFL fonts (Source Sans 3, Fraunces) instead of raw resource links from Info
  - [ ] Surface third-party licences through cashapp/licensee (`app.cash.licensee`)
      - Check AGP 9.x / Gradle 9.5 compatibility before picking a version. Do not pin one
        until that is confirmed.
      - Licensee reports the POM-declared licences of Gradle dependencies, so it covers the
        library set but not bundled assets. The two OFL font texts
        (`theme/src/main/res/raw/ofl_fraunces.txt`, `ofl_source_sans_3.txt`) are not in its
        output and need a hand-written entry shown alongside the generated list.
      - Those two files are referenced by nothing and are only kept in the APK by
        `theme/src/main/res/raw/keep.xml`. A screen that reads them removes that need.
  - Automate store-listing upload. `r0adkll/upload-google-play` carries the AAB, mapping and
    changelogs only, so screenshots, the feature graphic and the icon are hand-uploaded each
    year. They live under `assets/playstore/<year>/`, which is gitignored and local only. See
    `docs/playstore-capture.md`. Gradle Play Publisher or fastlane supply would cover them and
    would move the asset directory under a tool-owned path.

- [ ] Finish resource, accessibility, and preview coverage
  - Move remaining hardcoded UI text into string resources
  - Review content descriptions, touch targets, focus order, and color contrast
  - Add previews for the major screens and important component states
  - Pin the locale in `LocalDateFormatter`. Every helper except `formatToLocalWeekdayDate` uses
    `java.text.DateFormat`/`SimpleDateFormat` with no `Locale`, so the Programm day picker
    (`ProgramScreen.kt:78`) and all ticket and bus times render in the device locale, not
    German, on a non-German device. `localeFilters += setOf("de")` does not change this.
