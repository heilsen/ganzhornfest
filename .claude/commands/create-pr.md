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

2. If it passes:

   - Stage the change.
   - Commit with a terse Conventional Commit message. Follow the writing style in
     `CLAUDE.md`. No dash joining clauses. No semicolons. End the message with:

     ```
     Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>
     ```

   - Push the branch.
   - Open a PR against main:

     ```bash
     gh pr create --base main
     ```

     Keep the title and body terse and human-readable. Same style rules. End the body
     with one line stating that `./gradlew check` passed.
