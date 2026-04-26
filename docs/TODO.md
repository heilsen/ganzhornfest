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

- [ ] Remove or implement unused location capability
  - `ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION` are declared, but there is no visible runtime location feature

## UX And Feature Work

- [ ] Modernize the search screen
  - Replace the deprecated Material 3 `SearchBar` overload
  - Stop rendering `SearchScreenSuccess` twice
  - Replace the dropdown category picker with multi-select `FilterChip`s
  - Add query debouncing before hitting the database
  - Replace `LazyVerticalGrid(GridCells.Fixed(1))` with a `LazyColumn`
  - Add clearer empty-results and keyboard handling polish

- [ ] Improve detail and map flows
  - Allow the map to center on the selected club when entered from detail/search
  - Finish detail-to-detail navigation from related items using IDs
  - Replace the temporary placeholder in `DetailScreen` map interactions

- [ ] Polish map presentation and performance
  - Revisit the default `HYBRID` map type for the main festival map
  - Cache/precompute `MarkerUi.icon` instead of creating a `BitmapDescriptor` on every access
  - Keep the marker legend aligned with actual marker colors and resource strings

- [ ] Finish Info and Program screen polish
  - Replace hardcoded counts/text in `InfoScreen` with DB-backed values and string resources
  - Remove or fill the empty `Card` in `InfoScreen`
  - Rework the `ProgramScreen` into a timetable-style layout

- [ ] Consider small product additions
  - Instagram deep link
  - In-app review prompt

## Architecture And Data

- [ ] Move navigation and state handling out of composables where it is currently side-effect driven
  - Remove the `MainScreen` navigation TODO by emitting navigation effects instead of navigating inline
  - Keep presenters/view models predictable and easier to test

- [ ] Reduce lifecycle-sensitive presenter state
  - Revisit presenter-local `mutableStateOf` usage in `BusPresenter`, `ProgramPresenter`, and `SearchPresenter`
  - Keep presenter state derivation consistent across features

- [ ] Tighten database schema integrity and query performance
  - Add explicit foreign keys where missing
  - Add indexes for frequent lookups used by search and other list/detail flows
  - Add migration tests for SQLDelight
  - Consider moving large seed data into a format that is easier to diff and update yearly

- [ ] Improve info/statistics queries
  - Add the missing count queries needed for `InfoScreen` instead of manually maintained numbers

## Quality, Tooling, And Release

- [ ] Expand automated tests
  - Add presenter/view model tests for bus, program, search, map, and detail flows
  - Add DB integration and migration tests
  - Add UI tests for navigation paths such as Search -> Detail and Map -> Detail

- [ ] Add CI and static analysis
  - Add a CI pipeline for assemble, test, lint, and schema verification
  - Add Detekt and Ktlint
  - Enable Compose lint checks and address the findings

- [ ] Improve build and repo hygiene
  - Stop reading `local.properties` during Gradle configuration
  - Review whether `kapt` should be migrated to `ksp`
  - Ignore release artifacts such as `.aab` files outside `/app/release`

- [ ] Modernize Android setup
  - Switch `MainActivity` from `AppCompatActivity` to `ComponentActivity`
  - Add edge-to-edge/insets handling

- [ ] Update project documentation
  - Rewrite the README to describe the current Kotlin/Compose/SQLDelight app instead of the old Ionic/Cordova stack

- [ ] Improve release readiness and privacy
  - Add crash reporting with explicit privacy handling
  - Standardize logging and avoid sensitive logs in release builds

- [ ] Finish resource, accessibility, and preview coverage
  - Move remaining hardcoded UI text into string resources
  - Review content descriptions, touch targets, focus order, and color contrast
  - Add previews for the major screens and important component states
