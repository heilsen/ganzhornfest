# CLAUDE.md

How to work in this repository. Architecture, modules, DI, navigation, and
SQLDelight live in `README.md`.

## Workflow

Isolate each change in a git worktree off `main` so parallel agents do not share
a working tree.

Branch as `<type>/<slug>`. Type is one of feat, fix, refactor, chore, build,
docs.

Copy `local.properties` into the worktree. Gradle reads `google_maps_key` at
configuration time and fails without it. Do not copy `keystore.properties`.
`./gradlew check` never signs.

When the change is ready, run `./gradlew check`, then commit, push, and open a
PR against `main`.

## Verification

`./gradlew check` is the single gate. It runs unit tests, android lint, and
ktlint.

```bash
./gradlew check
./gradlew ktlintFormat
```

## Conventions

- Prefer small, module-local changes. Call out anything that touches DI,
  navigation, app startup, or DB schema.
- Match the existing patterns in the module you touch.
- When changing persisted data, edit the SQLDelight `.sq` schema and migrations.
  Do not patch generated artifacts.
- `docs/TODO.md` is the canonical backlog. Check it before proposing work that
  might already be tracked.

## Testing

Coverage is light. For logic changes add or extend tests where the module
already has test dependencies (`kotest`, `mockk`, `turbine`).

## Writing style

Applies to code comments, commit messages, PR titles, and PR bodies.

- Keep it terse and human-readable.
- Do not use a dash to join clauses. Split into separate sentences.
- Do not use semicolons. Use a list or separate sentences.
- Hyphenated words and CLI flags are fine.
