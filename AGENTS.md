# AGENTS.md

Use `README.md` as the source of truth for project overview, setup, module layout, and standard build commands. This file only captures agent-specific working guidance.

## Code Conventions For Agents

- Prefer small, module-local changes.
- Preserve existing package structure under `de.heilsen.ganzhornfest`.
- Follow the existing Compose patterns already used in the touched module; do not introduce a new architectural style for one screen.
- Keep Dagger wiring explicit and local to the owning module.
- When changing persisted data, update SQLDelight schema or migrations deliberately rather than patching generated artifacts.
- If a Gradle task fails unexpectedly, check whether `local.properties` is present; the current setup reads the Maps key during configuration.
- Treat `docs/TODO.md` as the best summary of current technical debt and planned cleanups.

## Testing Expectations

- Current automated test coverage is light. The only committed unit test currently lives in `feature/search-impl/src/test/...`.
- For logic changes, add or extend unit tests where the module already has test dependencies configured.
- For navigation, DI, or SQLDelight changes, at minimum run the most targeted Gradle task that exercises the changed module.

## Known Repo Realities

- Festival data is still partly hardcoded to the 2025 edition in code and resources.
- Some navigation/detail flows currently use titles instead of stable IDs.
- There may be uncommitted assets or local work in the tree; do not revert unrelated changes.

## Practical Guidance

- Before editing, inspect the owning module’s `build.gradle.kts` and nearby code to match its dependencies and patterns.
- Before proposing documentation changes, verify whether the information is already contradicted by `docs/TODO.md` or Gradle files.
- If you touch app startup, DI, navigation, or database schema, call that out explicitly in your summary because those are cross-cutting areas in this repo.
