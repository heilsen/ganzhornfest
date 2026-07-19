---
description: Start an isolated change in a fresh git worktree off master
argument-hint: <type> <slug>
---

Start a new isolated change. Type is `$1`. Slug is `$2`.

Steps:

1. Check `$1` is one of feat, fix, refactor, chore, build, docs. If not, stop and ask.
2. From the main checkout root, create a worktree and branch off master:

   ```bash
   git worktree add -b $1/$2 ../GanzhornfestCompose-$2 master
   ```

3. Bootstrap the worktree so Gradle can configure and build.

   Copy `local.properties`. It holds `google_maps_key`, which Gradle reads at
   configuration time.

   ```bash
   cp local.properties ../GanzhornfestCompose-$2/local.properties
   ```

   Write a stub `keystore.properties` into the worktree. Do not copy the real one.
   The android config reads these keys whenever the file exists, so every key must be
   present. The values are placeholders. `check` never signs, so they stay unused.

   ```bash
   cat > ../GanzhornfestCompose-$2/keystore.properties <<'EOF'
   storeFile=stub.keystore
   storePassword=stub
   release.keyAlias=stub
   release.keyPassword=stub
   upload.keyAlias=stub
   upload.keyPassword=stub
   EOF
   ```

4. Report the worktree path. Every edit for this change happens in that worktree, not in
   the main checkout. When the change is ready, run `/create-pr` from inside it.
