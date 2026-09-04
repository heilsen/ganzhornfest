# P2 · Search screen rewrite

## Goal

Fix the search screen so it renders once, uses a non-deprecated `SearchBar`, filters with
multi-select chips instead of a dropdown, doesn't hit SQLite on every keystroke, and shows a
real empty-results state.

## Why now

`feature/search-impl/src/main/kotlin/de/heilsen/ganzhornfest/search/SearchScreen.kt`:

- Renders `SearchScreenSuccess(...)` **twice** — once at line 120 inside the `SearchBar`'s
  trailing content lambda, once at line 136 in the `Scaffold`'s `content` slot. Both are live
  simultaneously whenever `searchModel is SearchModel.Data`, so results appear twice in the
  tree (only one is visible because the `SearchBar`'s expanded content overlays the rest, but
  it's dead weight and a maintenance trap).
- Uses the deprecated `SearchBar(query, active, onActiveChange, ...)` four-arg overload — a
  `@Deprecated` API in current Material3. The IDE already flags this.
- `query` lives in two places: local `remember { mutableStateOf("") }` in the screen (line 71)
  *and* `SearchModel.Data.query` from the presenter. They can drift.
- `LazyVerticalGrid(GridCells.Fixed(1))` (line 182) is a grid with one column — that's a
  `LazyColumn`. `docs/TODO.md` calls this out directly.
- The category picker is a dropdown (`SelectionCard`/`SelectionConfig`, single-select) for a
  three-value enum (`Category.Food`, `Drink`, `Club`) — `FilterChip`s in a `Row` are one tap
  instead of two, and `docs/TODO.md` asks for multi-select.
- `SearchPresenter.present()` (`SearchPresenter.kt:44`) calls `showResults(currentQuery,
  category)` directly off `LaunchedEffect`-collected event state — every keystroke that reaches
  `SearchEvent.Search` triggers a new `Flow` from `ShowSearchResultsUseCaseImpl`, which runs a
  `LIKE '%term%'` query against SQLite with no debounce.

## Worktree

```bash
/start-implement feat search-ux
```

## Files owned

- `feature/search-impl/src/main/kotlin/de/heilsen/ganzhornfest/search/**` (all of it:
  `SearchScreen.kt`, `SearchPresenter.kt`, `SearchEvent.kt`, `SearchViewModel.kt`,
  `ShowSearchResultsUseCaseImpl.kt`)
- `feature/search-api/src/main/kotlin/de/heilsen/ganzhornfest/search/**`
  (`Category.kt`, `SearchModel.kt`, `ShowSearchResultsUseCase.kt`) — only if the multi-select
  category change requires widening `Category` handling in the model; keep the `Category` enum
  itself unchanged unless truly necessary.
- `feature/search-impl/src/main/res/values/strings.xml`
- `feature/search-impl/src/test/kotlin/de/heilsen/ganzhornfest/search/ShowSearchResultsUseCaseTest.kt`

Fully isolated module pair. No overlap with either in-flight worktree or any other plan in this
batch.

## Steps

1. **Migrate to the current `SearchBar` API.** Material3 (`androidx-compose-material` =
   `1.3.2` per `libs.versions.toml`) exposes `SearchBar(inputField = { SearchBarDefaults.InputField(...) },
   expanded = ..., onExpandedChange = ..., content = ...)`. Replace the deprecated
   four-arg constructor call. Keep the same `topBar` placement inside `Scaffold`.
   *Verify:* no deprecation warning on the `SearchBar` call; `./gradlew :feature:search-impl:compileDebugKotlin`
   is clean.

2. **Delete the duplicate render.** Keep only the `content` slot's `SearchScreenSuccess` call
   (the `Scaffold.content` one at line 132–147); remove the one nested inside the `SearchBar`'s
   own content lambda. Confirm the `SearchBar`'s expanded-state content and the screen body
   don't need to differ — if `expanded` should show a distinct suggestions view eventually,
   that's future scope, not this plan.
   *Verify:* results render exactly once when scrolling the compose tree in layout inspector, or
   simply confirm by reading the final file that `SearchScreenSuccess` is called once.

3. **Single source of truth for the query.** Remove the local `remember { mutableStateOf("") }`
   in `SearchScreen`; drive the `SearchBar`'s `query` param from `searchModel.query` (already
   present on `SearchModel.Data`) and dispatch `SearchEvent.Search(it)` on change. The presenter
   already tracks `currentQuery` in `SearchPresenter.kt:23` — this makes the model the only
   owner.
   *Verify:* typing, clearing, and backing out all correctly reflect `searchModel.query` with no
   flicker (manual check via `./gradlew :app:installDebug` or a Compose UI test if time allows).

4. **Multi-select category chips.** Replace the `SelectionCard`/`SelectionConfig` dropdown for
   category with a `Row` of `FilterChip`s, one per `Category.entries`. Change
   `SearchEvent.ChangeCategory(category: Category)` to something like
   `SearchEvent.ToggleCategory(category: Category)`, and widen `SearchModel.Data.selectedCategory:
   Category` to `selectedCategories: PersistentSet<Category>`. Update
   `ShowSearchResultsUseCaseImpl` to accept a set and union results across selected categories
   (or query each selected category and merge — check what reads more naturally against the
   existing `when (category)` branches at `ShowSearchResultsUseCaseImpl.kt:29`).
   *Verify:* `ShowSearchResultsUseCaseTest` covers selecting zero, one, and multiple categories.

5. **`LazyVerticalGrid(GridCells.Fixed(1))` → `LazyColumn`.** Straight swap at
   `SearchScreen.kt:182`; the `items(searchModel.results)` block is unaffected.
   *Verify:* visual diff is a no-op for portrait; landscape no longer forces single-column grid
   overhead.

6. **Debounce.** In `SearchPresenter.present()`, wrap the query-driven flow with
   `snapshotFlow { currentQuery }.debounce(300.milliseconds).flatMapLatest { showResults(it,
   categories) }` instead of calling `showResults` inline on every recomposition triggered by
   `currentQuery` changing. `kotlinx.coroutines.flow.debounce` and `flatMapLatest` are already
   available via `libs.kotlinx.coroutines` on this module's classpath (check
   `feature/search-impl/build.gradle.kts` — it already depends on coroutines transitively via
   `:data`/`:core-api`; add `implementation(libs.kotlinx.coroutines)` explicitly if it's not a
   direct dependency).
   *Verify:* add a turbine-based presenter test (module doesn't have one yet — check for
   `test/kotlin/.../search/` presenter tests; if absent, this is the first) asserting that
   rapid successive `SearchEvent.Search` emissions produce only the final query's results.

7. **Empty-results state.** `SearchScreenSuccess` currently renders an empty `LazyColumn` with
   nothing when `results` is empty. Add a check using the existing `theme/component/EmptyScreen`
   composable (already used by `BusScreen` and `ProgramScreen` — `de.heilsen.ganzhornfest.theme.component.EmptyScreen`),
   with copy distinguishing "no query yet" from "no matches for your query."
   *Verify:* manual check with an empty DB query term vs. a nonsense term.

8. **IME polish.** Confirm `imeAction = ImeAction.Search` and `keyboardActions` dismiss the
   keyboard on submit; confirm the trailing clear icon (`Icons.Default.Clear`, line 107) also
   requests focus back to the field rather than losing focus entirely.
   *Verify:* manual check on device/emulator.

## Tests

`feature/search-impl` already has `kotest`, `mockk`, `turbine` wired
(`feature/search-impl/build.gradle.kts`) and one test file,
`ShowSearchResultsUseCaseTest.kt`. Extend it for multi-category selection (step 4). Add a new
`SearchPresenterTest.kt` using `turbine` for the debounce behavior (step 6) — there is no
existing presenter test in this module to pattern-match against, so follow the structure of
`feature/countdown/src/test/kotlin/.../CountdownUseCaseTest.kt` for style (plain Kotest, no
Compose test rule needed since `present()` can be exercised via `moleculeFlow` in a test scope
if you want full presenter coverage, or keep it at use-case level if that's simpler — use your
judgment on the right boundary here).

## Done when

`./gradlew check` passes, then `/create-pr`.
