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
- Dagger
- SQLDelight
- Kotlin Coroutines
- Kotlinx Serialization
- Molecule-style presenter/view-model flow

## Project Structure

- `:app` - Android application entry point, navigation, app DI
- `:theme` - shared Compose theme and reusable UI components
- `:presenter-api` - shared `MoleculeViewModel` base
- `:core-api`, `:core-impl`, `:core:datetime-api` - shared domain/config/date logic
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
- Android SDK / build tools compatible with `compileSdk 36`
- `local.properties` with a Google Maps API key:

```properties
google_maps_key=YOUR_API_KEY_HERE
```

The current Gradle setup reads `local.properties` during configuration from [`app/build.gradle.kts`](/Users/sebo/dev/code/ganzhornfest/GanzhornfestCompose/app/build.gradle.kts), so a missing key can break builds earlier than expected.

## Build And Test

Use the Gradle wrapper from the repo root:

```bash
./gradlew :app:assembleDebug
./gradlew test
./gradlew :feature:search-impl:test
./gradlew :app:lintDebug
```

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

Dagger 2 with a single `AppComponent` root (`:app`). Each feature module owns its `@Module` and optionally an `EntryPoint` interface. UI accesses the component via `rememberAppScope()` from `:di-api`.

### Navigation

Typed `@Serializable` destination objects in `Destination.kt`. Bottom nav covers Map, Program, Bus, and Info. Detail is a separate composable destination. Main navigation lives in `MainScreen.kt`.

### Module Dependency Pattern

Features with an `-api`/`-impl` split expose interfaces in `-api` and provide implementations in `-impl`. `:app` depends on all `-impl` modules; other features depend only on `-api` modules. Use `PersistentList` / `PersistentMap` (kotlinx-collections-immutable) for model state to keep Compose stability guarantees.

## Screenshots

Recent screenshots are available in [`assets/screenshots`](/Users/sebo/dev/code/ganzhornfest/GanzhornfestCompose/assets/screenshots).

## Status

This repository is actively being modernized. Some festival data and labels are still tied to the 2025 edition, and the main backlog is tracked in [`docs/TODO.md`](/Users/sebo/dev/code/ganzhornfest/GanzhornfestCompose/docs/TODO.md).

## License

MIT License

Copyright (c) 2015 Sebastian Heil
