# TODO

## High Priority

- [ ] Fix detail/navigation architecture
  - Replace title-based detail routing with stable IDs
  - Scope screen state to the `NavBackStackEntry` instead of using app-wide shared view models
  - Remove the `DetailEvent` push from `MainScreen` before navigation
  - Add deep-link and back stack restoration support once routes are ID-based

- [ ] Fill in missing loading and error states
  - Show a proper loading state in `MapScreen`
  - Show a proper loading state in `DetailScreen`
  - Add a reusable error state for failed DB/data loads instead of blank screens

- [ ] Use real festival opening times in time-based features
  - Stop hardcoding the bus start time to `19:00`
  - Introduce opening hours per day and use them for bus/program filtering

## UX And Feature Work

- [ ] Improve detail and map flows
  - Finish detail-to-detail navigation from related items using IDs
  - [x] Replace the temporary placeholder in `DetailScreen` map interactions (detail is a sheet on the live map)

- [ ] Polish map presentation and performance
  - Revisit the default `HYBRID` map type for the main festival map
  - Cache/precompute `MarkerUi.icon` instead of creating a `BitmapDescriptor` on every access
  - [x] Keep the marker legend colors aligned with the actual marker colors (labels are
    still hardcoded German strings, not resources, tracked below)

- [ ] Finish Info and Program screen polish
  - Replace hardcoded counts/text in `InfoScreen` with DB-backed values and string resources
  - [x] Remove or fill the empty `Card` in `InfoScreen`
  - Rework the `ProgramScreen` into a timetable-style layout

- [ ] Consider small product additions
  - [x] Instagram deep link
  - In-app review prompt

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

- [x] Improve info/statistics queries
  - [x] Add the missing count queries needed for `InfoScreen` instead of manually maintained numbers

## Quality, Tooling, And Release

- [ ] Expand automated tests
  - Add presenter/view model tests for bus, program, search, map, and detail flows
  - Add DB integration tests (migration path tests done, see `MigrationPathTest`)
  - Add UI tests for navigation paths such as Search -> Detail and Map -> Detail

- [ ] Add CI and static analysis
  - Add a CI pipeline for assemble, test, lint, and schema verification (done)
  - Add Ktlint (done, wired via the `ganzhornfest` convention plugin)
  - Enable Compose lint checks and address the findings (done, applied globally via the
    `ganzhornfest` convention plugin)
  - Add Detekt. Blocked: the last stable release (1.23.8) targets AGP 8.8.1 / Gradle
    8.12.1 / Kotlin 2.0.21, well behind this repo's AGP 9.2.0 / Gradle 9.4.1 / Kotlin
    2.2.20. The maintained line moved to plugin id `dev.detekt` and only exists as
    `2.0.0-alpha.x`, which has had Gradle configuration-cache serialization failures.
    Revisit once a stable `dev.detekt` release lands.

- [ ] Improve build and repo hygiene
  - Stop reading `local.properties` during Gradle configuration

- [ ] Modernize Android setup
  - Add edge-to-edge/insets handling

- [ ] Update project documentation
  - Rewrite the README to describe the current Kotlin/Compose/SQLDelight app instead of the old Ionic/Cordova stack

- [ ] Improve release readiness and privacy
  - Standardize logging and avoid sensitive logs in release builds
  - Add a licences screen for bundled OFL fonts (Source Sans 3, Fraunces) instead of raw resource links from Info

- [ ] Finish resource, accessibility, and preview coverage
  - Move remaining hardcoded UI text into string resources
  - Review content descriptions, touch targets, focus order, and color contrast
  - Add previews for the major screens and important component states
