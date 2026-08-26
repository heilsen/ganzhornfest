---
description: Verify with ./gradlew check, then commit and open a PR
---

Finish the current change and open it for review. Run this from inside the worktree for
this change.

1. Run the gate:

   ```bash
   ./gradlew check
   ```

   If it fails, stop. Show the failure. Do not commit. Do not open a PR.

2. Confirm git author email. GitHub maps `heilsen@sebastianheil.de` to
   SebastianHeil.

   ```bash
   test "$(git config user.email)" = "heilsen@sebastianheil.de"
   ```

   If this fails, stop. Do not commit. Tell the user to run
   `git config --local user.email heilsen@sebastianheil.de`.

3. If it passes:

   - Stage the change.
   - Commit with a terse Conventional Commit message. Follow the writing style in
     `CLAUDE.md`. No dash joining clauses. No semicolons. End the message with:

     ```
     Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>
     ```

   - Push the branch.
   - Confirm the GitHub CLI account is SebastianHeil. PRs and `gh pr merge`
     use that account, not `git user.email`.

     ```bash
     test "$(gh api user --jq .login)" = "SebastianHeil"
     ```

     If this fails, stop. The commit and push are fine. Do not open a PR.
     Tell the user to run `gh auth login` as SebastianHeil if that account is
     missing, then `gh auth switch --user SebastianHeil`.
   - Open a PR against main:

     ```bash
     gh pr create --base main
     ```

     Keep the title and body terse and human-readable. Same style rules. End the body
     with one line stating that `./gradlew check` passed.
