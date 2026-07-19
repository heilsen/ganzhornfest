# CLAUDE.md

Guidance for Claude Code working in this repository.

See `README.md` for architecture, module layout, DI, navigation, and the SQLDelight
setup. This file only covers how to work here.

## Workflow

Every change runs in its own git worktree off `master`, so parallel agents never edit
each other's files.

1. `/implement <type> <slug>` creates the worktree and branch `<type>/<slug>`, then
   bootstraps it (copies `local.properties`, writes a stub `keystore.properties`).
2. Make the change inside that worktree.
3. `/create-pr` runs `./gradlew check`. Only if it passes does it commit, push, and open
   a draft PR against `master`.

`type` is one of feat, fix, refactor, chore, build, docs.

## Verification

`./gradlew check` is the single gate. It runs unit tests, android lint, and ktlint.

- `local.properties` with `google_maps_key` is required. A missing key breaks Gradle at
  configuration time, not build time.
- Run `./gradlew ktlintFormat` to auto fix formatting.
- Never commit or open a PR when `check` is red.

## Conventions

- Prefer small, module-local changes. Call out anything that touches DI, navigation, app
  startup, or DB schema. Those are cross-cutting.
- Match the existing patterns in the module you touch. Do not introduce a new style for a
  single screen.
- When changing persisted data, edit the SQLDelight `.sq` schema and migrations. Do not
  patch generated artifacts.
- `docs/TODO.md` is the canonical backlog. Check it before proposing changes that might
  already be tracked or deferred.
- Inspect a module's `build.gradle.kts` before editing it.

## Testing

Coverage is light. Unit tests live in `:feature:search-impl` and `:feature:countdown`.
For logic changes add or extend tests where the module already has test dependencies
(`kotest`, `mockk`, `turbine`).

## Writing style

Applies to code comments, commit messages, PR titles, and PR bodies.

- Keep it terse and human-readable.
- Do not use a dash to join clauses. Split into separate sentences.
- Do not use semicolons. Use a list or separate sentences.
- Hyphenated words and CLI flags are fine.
