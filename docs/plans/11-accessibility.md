# P11 · Accessibility gate and sweep

## Goal

Make the app usable with TalkBack and at large font scale, and put a build gate in place so new
screens cannot regress it.

## ⚠ Blocked

Wave 3. Every Wave 1 and Wave 2 branch touches at least one screen this plan audits. Start only
after P1 through P10 have merged, then re-read every file fresh. The `file:line` citations below
are against `main` at `845e7fc` and will drift.

One specific collision to note: the unmerged `build/compose-lint` worktree (`27a786b`) already
moves `lintChecks(libs.compose.lint.checks)` out of `app/build.gradle.kts` and into
`build-logic/convention/src/main/kotlin/ganzhornfest.gradle.kts`. If that branch lands first, Step
1 shrinks to moving `lintConfig` only. Check before starting.

## Why now

The app has effectively no accessibility layer today.

**1. Semantics and content descriptions**

- One `contentDescription` exists app-wide, at `feature/search-impl/.../SearchScreen.kt:114`.
  Everything else uses the positional `Icon(vector, "literal")` overload or nothing.
- `MainScreen.kt:111,119,127,135` and `:153` pass a content description to an `Icon` that already
  sits next to a visible `Text` label inside `NavigationBarItem`, which merges descendants.
  TalkBack announces "Info, Info". These icons are decorative and should pass
  `contentDescription = null`.
- `theme/.../ticket/Ticket.kt` never merges semantics. Its `sideBar` slot is a separate `Column`
  at `weight(0.25f)` (`Ticket.kt:61`) rendered after the label/header/description column
  (`Ticket.kt:43`). TalkBack reads a program entry as stage, name, description, and only then the
  time. Four focus stops in the wrong order for every row of `ProgramScreen` and `BusScreen`.
- `bus-api/src/main/res/values/strings.xml` defines `bus_screen_content_description`
  ("Hier ist der Busfahrplan") which nothing references. Either wire it up or drop it.
- `map/.../MapScreen.kt:106-168` `Legend` is a color key. Each swatch is a bare `Box` with a
  background color and no semantics. The whole legend should be one merged node with a single
  description.
- `info-api/.../InfoScreen.kt:211-218` and `:238-245` use the raw URL as the visible link text.
  `withLink` gives correct link semantics, but TalkBack reads the URL character by character. Give
  the link a human label and keep the URL as the annotation target.

**2. Touch targets and focus order**

- `SearchScreen.kt:107-115` puts `Modifier.clickable` on a bare `Icon`. No 48dp minimum target and
  no `Role.Button`. It should be an `IconButton`, which supplies both.
- `bus-impl/.../BusScreen.kt:56-66` overlays "Angaben ohne Gewähr" on the connection list via
  `Modifier.align(Alignment.BottomEnd)` inside a `Box`. It sits last in traversal order but
  visually covers list content. Decide whether it belongs in the traversal at all.
- `MapScreen.kt:88-93` floats the `Legend` over the `GoogleMap`. `GoogleMap` wraps a `MapView`, so
  its internal accessibility is out of our control. Make sure the legend is reachable without
  traversing the map surface first.

**3. String resources**

Roughly fourteen hardcoded German literals block both translation and any real screen-reader
review. Module by module, with the target `strings.xml`:

- `bus-impl`: `BusTicket.kt:31` "Busfahrt Richtung", `BusScreen.kt:62` "Angaben ohne Gewähr",
  `:94` "Verbindungen (ab 19 Uhr)", `:109` "Bitte die Auswahl oben ändern", `:161`
  "Haltestelle ZOB/Ballei", `:165` "Von", plus "Nach" and "Abfahrt" at `:141,:147`. `bus-impl` has
  no `res/` directory yet. Strings can go in the existing `bus-api` one.
- `program`: `ProgramScreen.kt:36` "Programmplan", `:65` "Bühne", `:71` "Tag", `:87`
  "Kein Programm gefunden…". Module has no `res/` yet.
- `map`: the six `Legend` labels at `MapScreen.kt:113-165`. Module has no `res/` yet.
- `theme`: `EmptyScreen.kt:25` "Keine Daten verfügbar", `LoadingScreen.kt:24` "Lade Daten…",
  `GanzhornfestScaffold.kt:68` "zurück" (preview only). Module has no `res/` yet.
- `info-api`: `InfoScreen.kt:225`.
- `map/detail`: `DetailScreen.kt:40` "zurück", `:69-72` "Angebot" / "Vereine".
- Shared "zurück" appears three times. Put one `back` string in `core-api` or `theme` and reuse
  it.

`SearchScreen.kt` resolves its strings through the injected `ResourcesProvider`
(`core-impl/.../ResourcesProviderImpl.kt`), which holds an `AppScope` `Context`. That bypasses the
composition's configuration, unlike `stringResource()` which reads `LocalContext`. Standardize
composables on `stringResource()` and leave `ResourcesProvider` for non-composable callers.

**4. Font scaling and contrast**

- `Ticket.kt:43,61` splits width with fixed weights `0.755f` / `0.25f`. At 200% font scale the 25%
  sidebar has to fit a wrapped time range. `ProgramScreen.kt:118-129` builds that string with
  embedded newlines, and `BusTicket.kt:56` hard-wraps the bus line on "/". Both need checking at
  large scale.
- `BusTicket.kt:88-89` sets `maxLines = 1` with `TextOverflow.Ellipsis` on intermediate stop
  names. Verify whether truncated stops are still announced in full.
- Contrast is narrow in scope. `theme/Theme.kt` uses `dynamicLightColorScheme` /
  `dynamicDarkColorScheme` on API 31+ and falls back to the Material defaults below, so scheme
  pairs are already tonally correct. The unverified colors are the six hardcoded hex literals at
  `MapScreen.kt:117,126,135,144,153,162` painted on `surfaceVariant`. Check those against 3:1 for
  non-text graphics and move them into the color scheme or a named palette.
- Unrelated dead code worth mentioning but not deleting: `theme/Color.kt` defines `Purple200`,
  `Purple500`, `Purple700`, `Teal200` and nothing references them since `Theme.kt` switched to
  generated schemes.

## Worktree

```bash
/start-implement feat accessibility
```

## Files owned

- `build-logic/convention/src/main/kotlin/ganzhornfest.gradle.kts`
- `app/build.gradle.kts` (remove the module-local `lint` block once it moves), `app/lint.xml`
  (moves to the repo root)
- New `src/main/res/values/strings.xml` under `bus-impl`, `program`, `map`, `theme`
- `theme/.../component/ticket/Ticket.kt`, `EmptyScreen.kt`, `LoadingScreen.kt`,
  `GanzhornfestScaffold.kt`
- `app/.../main/MainScreen.kt`
- `bus-impl/.../BusScreen.kt`, `BusTicket.kt`
- `program/.../ProgramScreen.kt`
- `map/.../map/MapScreen.kt`, `map/.../detail/DetailScreen.kt`
- `feature/search-impl/.../SearchScreen.kt`
- `info-api/.../InfoScreen.kt`
- `docs/TODO.md` (tick off the accessibility bullets at `93-96`)

**Do not touch:** presenters, view models, use cases, `.sq` files, navigation routes. If a fix
seems to need a model change, note it and move on. This plan is presentation-layer only.

## Steps

1. **Move the lint gate into the convention plugin.** Add an `androidComponents`-guarded `lint`
   block to `ganzhornfest.gradle.kts` so every module inherits `lintConfig` and
   `lintChecks(compose-lint-checks)`. Move `app/lint.xml` to the repo root and point every module
   at it. Drop the now-redundant block in `app/build.gradle.kts:90-92` and the `lintChecks` line
   at `:146`.
   *Verify:* `./gradlew :program:lint` reports `HardcodedText` errors it previously never ran.
   Expect a wall of findings. That is the point.

2. **Extract strings.** Work module by module through the list under "3. String resources" until
   `./gradlew lint` is clean of `HardcodedText`. Create `src/main/res/values/strings.xml` where
   the module has no `res/` yet. Use `stringResource()` in composables, not `ResourcesProvider`.
   *Verify:* `./gradlew lint` passes with `HardcodedText` at error severity.

3. **Fix icon semantics.** Set `contentDescription = null` on the five decorative icons in
   `MainScreen.kt` that sit beside visible labels. Give every genuinely standalone icon a
   `stringResource` description. Convert `SearchScreen.kt:107-115` from `Icon` + `clickable` to
   `IconButton`.
   *Verify:* TalkBack announces each bottom-bar tab once, not twice. The search clear control
   announces as a button and has a 48dp target.

4. **Merge ticket semantics.** Add a
   `Modifier.semantics(mergeDescendants = true) { contentDescription = … }` to the `Ticket` root
   in `Ticket.kt`, composed in reading order (time first, then stage, then name, then
   description). Pass the assembled description in from `BusTicket` and `ProgramScreen` rather
   than deriving it inside `Ticket`, since only the callers know the field meanings.
   *Verify:* one focus stop per row, announced in the intended order.

5. **Group the map legend.** Wrap `Legend`'s `Column` in a merged semantics node with one
   description. Move the six hex literals into named values and check each against
   `surfaceVariant` at 3:1.
   *Verify:* Accessibility Scanner reports no contrast findings on the legend.

6. **Label the Info links.** Replace the raw-URL link text at `InfoScreen.kt:211-218` and
   `:238-245` with a human label. Keep the `LinkAnnotation.Url` target.
   *Verify:* TalkBack reads the label, not the URL character by character.

7. **Font scaling pass.** Run every screen at 200% font scale. Fix clipping in `Ticket`'s
   `weight(0.25f)` sidebar, the newline-built time string in `ProgramScreen.kt:118-129`, and the
   `maxLines = 1` stop names in `BusTicket.kt:88-89`.
   *Verify:* add `@Preview(fontScale = 2f)` variants next to the existing `@PreviewDefault`
   annotations. Check `core-api`'s `PreviewDefault` first. If it is a `@Preview` multi-annotation,
   a `fontScale = 2f` entry can be added there once instead of per screen.

8. **Write the manual checklist.** Fill in the checklist below so the audit is repeatable next
   festival season.

## Manual verification checklist

Run with TalkBack enabled and with Accessibility Scanner, once per screen. Repeat at 200% font
scale (Settings → Display → Font size).

| Screen | Reading order correct | Targets ≥ 48dp | All controls labeled | Survives 200% scale |
|---|---|---|---|---|
| Home / MainScreen (nav bar, FAB) | | | | |
| Info | | | | |
| Map | | | | |
| Program | | | | |
| Bus | | | | |
| Search | | | | |
| Detail (club/offer) | | | | |

## Tests

No `androidTest` source set exists anywhere in the repo and this plan does not add one.
Verification is the lint gate plus the manual checklist above. If Step 4 pushes
description-assembly logic into a plain function, unit test that function in whichever module
already has `kotest` and `mockk` wired up.

## Done when

`./gradlew check` passes with lint applied to every module, the manual checklist above is filled
in for all seven screens, and the accessibility bullets in `docs/TODO.md:93-96` are ticked. Then
`/create-pr`.
