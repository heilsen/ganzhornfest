# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./gradlew :app:assembleDebug         # build debug APK
./gradlew test                        # run all unit tests
./gradlew :<module>:test              # e.g. :feature:search-impl:test
./gradlew :app:lintDebug              # Android lint
```

`local.properties` with `google_maps_key` is required. A missing key breaks Gradle **at configuration time**, not build time.

## Working Conventions

- Prefer small, module-local changes. Explicitly call out any changes that touch DI, navigation, app startup, or DB schema — those are cross-cutting.
- Match the existing patterns in the module you're touching. Don't introduce a new architectural style for a single screen.
- When modifying persisted data, edit the SQLDelight `.sq` schema files and any migrations. Don't patch generated artifacts.
- `docs/TODO.md` is the canonical backlog — check it before proposing changes that might already be tracked or intentionally deferred.
- Before editing, inspect the module's `build.gradle.kts` to understand its dependencies and active plugins.

## Testing

Coverage is light. The only current unit test is in `:feature:search-impl`. For logic changes, add or extend tests where the module already has test dependencies (`kotest`, `mockk`, `turbine`). For DI, navigation, or schema changes, at minimum run the most targeted Gradle task covering the changed module.
