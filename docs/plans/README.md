# Improvement plan batch

Twelve plans generated from `docs/TODO.md`, the six-item shortlist below, and a read of the
current codebase against two in-flight worktrees (`feat/json-seed`, `feat/festival-data-2026`).
P12 was added later, from findings in the 2026 Play Store asset capture.
Weighted towards UX/UI, then new features, then crash reporting for insight, per the original ask.

| Item | Plan |
|---|---|
| JSON Seed | P10 |
| Crash Reporting | P1 |
| Search UX rewrite | P2 |
| Loading/error polish | P4 |
| Nav IDs/shared VM cleanup | P8 |
| Bus extra trips if unpublished | P5 |
| Accessibility | P11 |

## Run P7 first, alone

`.github/workflows/ci.yml` still triggers on `branches: [master]`. The default branch has been
`main` since `bcd72f0`. `gh run list` shows exactly two CI runs, ever, both from before the
rename. Every PR since has merged with zero automated verification. **Land P7 before opening any
other PR in this batch** — otherwise none of them get checked either.

## Wave 1 — launch in parallel now

Disjoint file footprints, verified against both in-flight worktrees' `git status --short`
output. Safe to hand to seven agents simultaneously.

| # | Branch | Footprint | One-line |
|---|---|---|---|
| P7 | `build/ci-and-static-analysis` | `.github/`, `build-logic/`, `libs.versions.toml` | Fix the dead CI trigger, add Detekt + global Compose lint |
| P1 | `feat/crashlytics` | `app/**` (di, build files), new `core-api` `CrashReporter` | Firebase Crashlytics, release-only Timber tree, consent gate |
| P2 | `feat/search-ux` | `feature/search-impl/**`, `feature/search-api/**` | Fix duplicate render, debounce, multi-select chips |
| P3 | `feat/program-timetable` | `program/**` | All-stages timetable with sticky time headers |
| P4 | `feat/loading-error-states` | `theme/**`, `map/.../detail/**`, `bus-impl/**` | New `ErrorScreen`, fix `DetailScreen`'s blank-screen bailout |
| P5 | `feat/bus-opening-hours` | `core-api/**`, `core-impl/**`, `bus-api/**`, `bus-impl/**`, one query in `BusLine.sq` | Real per-day hours, split "unpublished" from "no match", DB-derive the destination list |
| P6 | `feat/edge-to-edge` | `app/**` (activity, manifest, theme), `theme/.../GanzhornfestScaffold.kt` | Edge-to-edge, predictive back, fix AppCompat/Compose theme clash |

**Overlaps, both trivial:** `app/build.gradle.kts` (P1 adds plugins, `feat/festival-data-2026`
bumps `versionCode`) and `gradle/libs.versions.toml` (P1 and P7 both add entries, different
keys).

## Wave 2 — blocked on `feat/festival-data-2026` landing

These touch files your festival-data branch is actively editing. Do not start until it merges,
and re-read the affected files fresh at that point — line references in these plans are against
pre-merge `main`.

| # | Branch | Footprint | Blocked by | One-line |
|---|---|---|---|---|
| P8 | `refactor/nav-ids-and-scoped-vms` | `app/.../navigation`, `app/.../main/MainScreen.kt`, `map/.../detail/**`, `.sq` files | `MainScreen.kt`, `.sq` files under edit | ID-based routes, effect-driven nav, scoped ViewModels |
| P9 | `feat/map-polish` | `map/src/main/kotlin/.../map/**` | Your branch owns all of `:map` | Cache marker icons, stop crashing on unknown POI types, center on club |
| P10 | `chore/db-integrity-and-seed` | `.gitignore` (do now), `.sq` files, `database/build.gradle.kts` | Both `feat/json-seed` and `feat/festival-data-2026` add `migrations/3.sqm` | Fix the assets gitignore bug, resolve the migration collision, then harden schema |

**P10's first two steps are not actually blocked** — the `.gitignore` fix (`assets/` →
`/assets/`) is a one-line, standalone, mergeable-today fix. It's listed under Wave 2 only
because its later steps (schema hardening) need the migration collision resolved first. Consider
splitting it into its own tiny PR immediately; the plan file describes this explicitly.

## Wave 3 — after the batch lands

| # | Branch | Footprint | Blocked by | One-line |
|---|---|---|---|---|
| P11 | `feat/accessibility` | every UI module plus `build-logic/` | all of Wave 1 and Wave 2 | Lint gate for every module, then a TalkBack, touch target, string and font scale sweep |
| P12 | `feat/tablet-adaptive-layout` | `app/.../MainScreen.kt`, `theme/**`, `program/**`, `bus-impl/**`, `libs.versions.toml` | P8, P4, P5 | `NavigationSuiteScaffold` for a rail at width, cap single-column content at ~600 dp |

## Deliberately excluded from this batch

- **Instagram deep link, in-app review prompt** — natural home is `InfoScreen`, which
  `feat/festival-data-2026` already rewrites (+87/-lines per its diff). Not enough surface area
  to justify a standalone plan once that lands.
- **README rewrite** — already current, not stale.
- **`local.properties` read at Gradle configuration time** — real but low-impact, and it shares
  `app/build.gradle.kts` with P1; fold into a future pass instead of adding an eighth Wave 1
  branch.

## Format

Each plan file: Goal, Why now (with `file:line` citations against `main`), Worktree command,
Files owned + do-not-touch list, ordered Steps with inline verification, Tests, Done when. Wave
2 and Wave 3 plans additionally open with a ⚠ Blocked section.

None of this is committed yet — these are working files under `docs/plans/`, `git status` will
show them as untracked until you decide to commit.
