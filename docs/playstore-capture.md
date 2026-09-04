# Play Store listing capture

How to reproduce the Play Store screenshots, feature graphic, and icon for a festival edition.

Output goes to `assets/playstore/<year>/`:

```
assets/playstore/<year>/
├── phone-NN-<screen>.png    5 shots, bottom-nav order: phone-01-info phone-02-karte phone-03-programm phone-04-bus phone-05-suche
├── tablet-NN-<screen>.png   same screens in landscape
└── graphics/                icon-512.png, feature-graphic-1024x500.png
```

`/assets/` is gitignored, so every file here is local only. Back the directory up outside the repo
after each run. This document is the tracked part. The images are not.

## Build

`./gradlew assembleRelease`, signed. The release build is required. The debug build shows a
"Standorte korrigieren" pin editor over the map and is labelled "Ganzhornfest Debug".

The 2026 run shipped `versionName 2026.2.1`.

## Both devices

- Per-app language German:
  `adb shell cmd locale set-app-locales de.heilsen.ganzhornfest --locales de-DE`. This also flips
  `Locale.getDefault()` for the app process, which the date and time formatters depend on. Skip it
  and the dates render in the device locale. The 2024 tablet run predates this step and shipped with
  `Saturday, August 31, 2024` and `4:00 PM` in the Programm and Bus shots. See the locale note in
  `docs/plans/05-bus-opening-hours.md`.
- Light theme (default).
- Status bar cleaned with SysUI demo mode:
  ```bash
  adb shell settings put global sysui_demo_allowed 1
  adb shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 0930
  adb shell am broadcast -a com.android.systemui.demo -e command battery -e level 100 -e plugged false
  adb shell am broadcast -a com.android.systemui.demo -e command network -e wifi show -e level 4
  adb shell am broadcast -a com.android.systemui.demo -e command notifications -e visible false
  ```
- 24-hour clock: `adb shell settings put system time_12_24 24`.

## Phone

AVD `Pixel_API_34` (`sdk_gphone64_arm64`, API 34).

- `adb shell wm size 1080x1920`, density left at 440. Yields a 9:16 frame inside Play's 2:1 cap and
  keeps the Compact width class.
- API 34 was used for the shipped shots. `Pixel_9a_API_36` also works once its Play services have
  updated. See the Maps note below.

## Tablet

AVD `Medium_Tablet_API_34` (native 1600x2560 at 320 dpi).

- `adb shell wm size 1440x2560` in the natural portrait orientation, then
  ```bash
  adb shell settings put system accelerometer_rotation 0
  adb shell settings put system user_rotation 1
  ```
  for landscape. Result is exactly 2560x1440, 16:9, 800x450 dp. At 800 dp width
  `isSidePanelLayout()` returns true, so Detail renders as a side pane.

Only the Karte screen is truly tablet-adapted. Programm, Info and Bus keep the bottom
`NavigationBar` and a single column at any width, so they read as a stretched phone. Play needs a
minimum of four shots. `docs/plans/12-tablet-adaptive-layout.md` tracks the fix.

## Flatten to RGB

`adb screencap` emits an alpha channel. Play wants 24-bit RGB PNG under 8 MB, so flatten every
capture (Pillow, `Image.convert("RGB")`, or equivalent).

## Feature graphic

Authored as a self-contained HTML file, rendered by headless Chrome at 1024x500, flattened to RGB.
Fonts are the app's own `theme/src/main/res/font/` faces (Fraunces SemiBold, Source Sans 3).
Palette from `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/Color.kt`: wine `#7A1F2B`, gold
`#C4A35A`, cream `#FFF8F0`.

`graphics/icon-512.png` is a copy of `app/src/main/ic_launcher-playstore.png`, 32-bit PNG, opaque.

## Maps tile rendering

The map needs Google Play services for HYBRID tiles. On a first cold boot of `Pixel_9a_API_36` the
bundled GMS is stale and the tile token request fails with
`Error requesting API token, StatusCode=UNAVAILABLE`, leaving the map blank. This is not a signing
or API-key problem. The release SHA-1 is already registered on the Maps key, and the same signed
APK renders fine once GMS settles.

Give the emulator a cold boot with network and a few minutes. GMS self-updates (seen here to
`26.32.34`), and the release-signed build then renders the HYBRID map with no code or Cloud console
change. API 34 images happen to ship a GMS recent enough to skip that wait, which is why the shipped
shots used them.

The `GoogleCertificatesRslt: not allowed` line in logcat is a red herring. It comes from
`PhFlagUpdateRegistry` (Phenotype flag fetch) and every non-Google app logs it. It does not block
map rendering.

## Restoring the emulators

```bash
adb -s <serial> shell wm size reset
adb -s <serial> shell wm density reset
adb -s <serial> shell settings put global sysui_demo_allowed 0
adb -s <serial> shell am broadcast -a com.android.systemui.demo -e command exit
adb -s <serial> shell settings put system accelerometer_rotation 1
```
