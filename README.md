# Ganzhornfest

Android app for visitors of the Ganzhornfest in Neckarsulm, Germany.

The app provides festival information in German, including:

- map of clubs, stands, and points of interest
- food and drink search
- stage program
- bus departure information
- festival info and opening-day related content

## Tech Stack

- Kotlin
- Android Gradle Plugin
- Jetpack Compose
- Navigation Compose
- Metro (compiler plugin DI)
- SQLDelight
- Kotlin Coroutines
- Kotlinx Serialization
- Molecule (Compose runtime presenters)
- Google Maps Compose
- Timber
- Firebase Crashlytics

## Project Structure

- `:app` - Android application entry point, navigation, app DI
- `:theme` - shared Compose theme and reusable UI components
- `:presenter-api` - shared `MoleculeViewModel` base
- `:core-api`, `:core-impl` - shared domain/config/date logic
- `:data` - repository layer over SQLDelight queries
- `:database` - SQLDelight schema, migrations, Android DB wiring
- `:map` - map and detail flows
- `:program` - festival program feature
- `:bus-api`, `:bus-impl` - bus feature
- `:feature:search-api`, `:feature:search-impl` - search feature
- `:info-api` - info screen surface
- `:di-api` - DI scopes and component access helpers

## Requirements

- JDK 21
- Android SDK / build tools compatible with `compileSdk 37`
- `local.properties` with a Google Maps API key:

```properties
google_maps_key=YOUR_API_KEY_HERE
```

The current Gradle setup reads `local.properties` during configuration from
[`app/build.gradle.kts`](app/build.gradle.kts), so a missing key can break builds
earlier than expected.

The app ships German only. `localeFilters` is set to `de`, the default `values/`
resources *are* the German ones, and there is no `values-de`.

## Build And Test

Use the Gradle wrapper from the repo root:

```bash
./gradlew check              # the full gate: unit tests, android lint, ktlint
./gradlew :app:assembleDebug
./gradlew test
./gradlew :feature:search-impl:test
./gradlew :app:lintDebug
./gradlew ktlintFormat       # auto fix formatting
```

`./gradlew check` is the single verification gate. ktlint is wired in through
the `ganzhornfest` convention plugin in `build-logic`.

## Architecture

The app uses a **Molecule-based presenter pattern** layered as:

```
UI Screen → ViewModel → Presenter (@Composable) → Use Case → Repository → SQLDelight DB
```

- **`MoleculeViewModel<Event, Model>`** (`:presenter-api`) — base class. Events flow in via `take(event)`; the derived `StateFlow<Model>` flows out to the UI.
- **Presenter** — a `@Composable fun present(events: Flow<Event>): Model`. Uses `remember`, `LaunchedEffect`, and `collectAsState` to derive state reactively.
- **Use Cases** — interfaces in `-api` modules, implemented in `-impl` modules.
- **Repositories** (`:data`) — wrap SQLDelight queries into `Flow<T>`.
- **Database** (`:database`) — SQLDelight `.sq` schema files are the source of truth for the pre-seeded SQLite DB.

### Dependency Injection

Metro (`dev.zacsweers.metro`), a compiler plugin DI framework. Not Dagger, Hilt, or Koin. A single `@DependencyGraph(AppScope::class)` root lives in `:app` (`di/AppComponent.kt`) and is created with `createGraphFactory` in `GanzhornfestApplication`. Bindings use `@Inject constructor`, `@ContributesBinding`, `@ContributesTo`, `@Provides`, and `@BindingContainer`. UI reaches the graph via `rememberAppGraph()` from `:di-api`.

### Adding A Feature

`:feature:search-api` and `:feature:search-impl` are the cleanest template. A feature module usually holds:

- a `Model` sealed interface
- a `UseCase` with a unit test
- a `@Composable` presenter
- a `MoleculeViewModel` subclass
- the Compose screen

Register the new module in `settings.gradle.kts`.

### Navigation

Typed `@Serializable` destination objects in `Destination.kt`. Bottom nav covers Info, Map, Program, and Bus, in that order. Detail is a separate composable destination. Main navigation lives in `MainScreen.kt`.

### Module Dependency Pattern

Features with an `-api`/`-impl` split expose interfaces in `-api` and provide implementations in `-impl`. `:app` depends on all `-impl` modules; other features depend only on `-api` modules. Use `PersistentList` / `PersistentMap` (kotlinx-collections-immutable) for model state to keep Compose stability guarantees.

## Contributing

Working conventions, the git-worktree-per-change workflow, and the writing style
live in [`CLAUDE.md`](CLAUDE.md).

CI runs `./gradlew assembleDebug check` on JDK 21 for every push to `main` and
every PR against it. The workflow writes `local.properties` from the
`GOOGLE_MAPS_KEY` repository secret, so a fork without that secret fails during
Gradle configuration. See [`.github/workflows/ci.yml`](.github/workflows/ci.yml).

## Screenshots

Recent screenshots are available in `assets/playstore/<year>/`.

## Status

This repository is actively being modernized. Some festival data and labels are still tied to the 2025 edition, and the main backlog is tracked in [`docs/TODO.md`](docs/TODO.md).

## License

MIT, see [`LICENSE`](LICENSE).

Copyright (c) 2015 Sebastian Heil

The bundled fonts, Fraunces and Source Sans 3, are used under the SIL Open Font
License 1.1. Their notices ship in `theme/src/main/res/raw/`.
