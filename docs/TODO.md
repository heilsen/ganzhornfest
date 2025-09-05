# Internal
1. [ ] PlayStore release readiness
    - [ ] Add crash reporting (e.g., Firebase Crashlytics) with opt-in analytics respecting privacy
    - [ ] Standardize Timber usage levels; add structured logging for critical flows

2. [ ] Detail Screen
    - [ ] Replace passing detail type as String with a sealed class or enum across navigation
      routes; prefer IDs over titles for selection
    - [ ] Add deep links and saved state handling for back stack restoration
    - [ ] Start destination: configure to Map when Details exist; remove temporary TODO

3. [ ] Search screen polishing
    - [ ] Replace SearchBar Composable with newest SearchBar composable
    - [ ] Replace dropdown with multi-selection FilterChips
    - [ ] Optimize search query performance; debounce input and leverage SQL indexes
    - [ ] Add navigation into Details using IDs and typed routes

4. [ ] Map screen improvements
    - [ ] Implement marker clustering if number of markers is large
   - [ ] Add legend for marker colors; ensure consistent MarkerUiType to hue mapping (
     map/MarkerUi.kt)
    - [ ] Preload marker icons to avoid jank; consider using custom bitmaps instead of default hues

5. [ ] Program and Info screens enhancements
    - [ ] Replace hardcoded counts in InfoScreen with dynamic SQL query
    - [ ] Implement timetable layout for Program screen as per reference link; extract shared
      Selection components and unify with SelectionScreen

6. [ ] Compose best practices and performance
    - [ ] Enable Compose Compiler metrics and reports; fix recomposition hotspots (too many
      recompositions in BusScreen)
    - [ ] Mark stable UI models with @Stable or use immutable types; ensure MarkerUi is stable or precompute icon via remember (file: map/MarkerUi.kt)
    - [ ] Avoid heavy work inside composables; move IO/state transformations out of composition where possible
    - [ ] Ensure state hoisting: presenters should remain pure w.r.t. side-effects
    - [ ] Add Previews for all major screens and reusable components
    - [ ] MarkerUi currently constructs a BitmapDescriptor per instance; consider caching or using remember in composables for performance and to avoid allocations.

7. [ ] Time and date API consistency
    - [ ] Introduce a small DateTimeMapper to convert to/from SQL and Instant with explicit formatters

8. [ ] Database: schema quality and data integrity (SQLDelight)
    - [ ] Add foreign key constraints and indices for poiCoordinate(poiId, coordinateId) if not present; enforce referential integrity
    - [ ] Investigate duplicate insertion: poiCoordinate has two inserts for poiId=40 (lines 471 and 480). Confirm intentional multiplicity; otherwise remove duplicates or add a unique constraint where appropriate (file: database/.../migrations/1.sqm)
    - [ ] Normalize types and add NOT NULL constraints where applicable
    - [ ] Consider seeding data via CSV or structured files to reduce massive INSERT noise and support diffing
    - [ ] Add migration tests to ensure schema can be created and migrated on devices
    - [ ] Add indexes for frequent lookups used by Search/Map/Program screens

9. [ ] Data layer improvements
    - [ ] Create repository interfaces in api modules; keep DB specifics in impl. Ensure data module responsibilities are clear
    - [ ] Replace string-based date filtering in repositories with proper typed parameters and SQL parameter binding
    - [ ] Add caching strategies where appropriate

10. [ ] Code quality: static analysis and style
    - [ ] Enable Compose Lint and address reported issues
    - [ ] Add Detekt and Ktlint with a shared baseline; integrate into CI
    - [ ] Ensure public APIs have KDoc in api modules

11. [ ] Testing: unit, UI, and integration
    - [ ] Add unit tests for presenters (BusPresenter, ProgramPresenter, SearchPresenter) with
      Turbine for Flow testing
    - [ ] Add DB integration tests for SQLDelight queries and migrations
    - [ ] Add UI tests for navigation (Detail flow, Search to Detail, Map to Detail)
    - [ ] Add snapshot tests for Compose components where stable
    - [ ] Introduce test fixtures for sample data

12. [ ] Build and CI/CD
    - [ ] Add GitHub Actions (or equivalent) pipeline: assemble, test, lint, detekt, ktlint, and build cache config
    - [ ] Enable Gradle Build Scan and configuration cache where compatible
    - [ ] Use dependency updates plugin to monitor outdated libraries
    - [ ] Add release signing and Play Store upload tasks if applicable

13. [ ] Resource and i18n improvements
    - [ ] Ensure all user-facing strings are in string resources; remove hardcoded text

14. [ ] Accessibility and theming
    - [ ] Add content descriptions for icons (already partially used) and verify accessibility labels
    - [ ] Verify dynamic color and high-contrast support in GanzhornfestTheme
    - [ ] Ensure minimum hit targets, focus order, and TalkBack flows on all screens
    - [ ] Validate color contrast for map marker hues and legend if any

15. [ ] Security and privacy
    - [ ] Ensure no sensitive logs in release builds

16. [ ] Performance checks
    - [ ] Profile cold start; verify Dagger component creation and DB driver init overhead
    - [ ] Cache heavy resources and precompute maps data on background threads
    - [ ] Use remember and derivedStateOf judiciously to minimize recompositions


### Features
- [ ] Instagram Deep Link
    - https://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
    - https://stackoverflow.com/questions/15497261/open-instagram-user-profile
- [ ] replace hardcoded, manually counted number in InfoScreen with dynamic SQL query
- [ ] In-app review
- [ ] Search Screen: Use multi selection FilterChips instead of dropdown menu
- [ ] Make Program Screen look like this https://www.maifeld-derby.de/timetable
- [ ] Use IDs in Detail Screen instead of name to select corresponding detail item (club, offer)