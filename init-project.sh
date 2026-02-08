#!/bin/bash

# ==================================================
# INPUT
# ==================================================
NEW_APP_NAME=$1
OLD_PACKAGE=$2
NEW_PACKAGE=$3

if [ -z "$NEW_APP_NAME" ] || [ -z "$OLD_PACKAGE" ] || [ -z "$NEW_PACKAGE" ]; then
  echo "❌ Usage:"
  echo "   ./init-project.sh \"My App\" com.old.package com.new.package"
  exit 1
fi

echo "🚀 Initializing Android multi-module project..."
echo "App Name     : $NEW_APP_NAME"
echo "Old Package  : $OLD_PACKAGE"
echo "New Package  : $NEW_PACKAGE"

# ==================================================
# PATH HELPERS
# ==================================================
OLD_PATH=$(echo $OLD_PACKAGE | tr '.' '/')
NEW_PATH=$(echo $NEW_PACKAGE | tr '.' '/')

# ==================================================
# MODULE LIST (EDITABLE)
# ==================================================
MODULES=(
  "app"
  "features/setting"
  "features/dashboard"
  "repositories"
  "core/ui"
  "core/network"
)

SOURCE_SETS=("main" "test" "androidTest")

# ==================================================
# 1. Replace package references in all modules
# ==================================================
echo "🔁 Replacing package references..."

for MODULE in "${MODULES[@]}"; do
  if [ -d "$MODULE" ]; then
    grep -rl "$OLD_PACKAGE" "$MODULE" | xargs sed -i '' "s/$OLD_PACKAGE/$NEW_PACKAGE/g"
  fi
done

# ==================================================
# 2. Rename directory structures
# ==================================================
echo "📁 Updating folder structures..."

for MODULE in "${MODULES[@]}"; do
  for SRC in "${SOURCE_SETS[@]}"; do
    BASE="$MODULE/src/$SRC/java"
    if [ -d "$BASE/$OLD_PATH" ]; then
      mkdir -p "$BASE/$(dirname $NEW_PATH)"
      mv "$BASE/$OLD_PATH" "$BASE/$NEW_PATH"
    fi
  done
done

# ==================================================
# 3. Update app name (only app module)
# ==================================================
echo "✏️ Updating app name..."

STRINGS_FILE="app/src/main/res/values/strings.xml"
if [ -f "$STRINGS_FILE" ]; then
  sed -i '' "s|<string name=\"app_name\">.*</string>|<string name=\"app_name\">$NEW_APP_NAME</string>|g" "$STRINGS_FILE"
fi

# ==================================================
# 4. Update Gradle configs (namespace / appId)
# ==================================================
echo "⚙️ Updating Gradle configs..."

for MODULE in "${MODULES[@]}"; do
  if [ -f "$MODULE/build.gradle.kts" ] || [ -f "$MODULE/build.gradle" ]; then
    grep -rl "$OLD_PACKAGE" "$MODULE"/build.gradle* | xargs sed -i '' "s/$OLD_PACKAGE/$NEW_PACKAGE/g"
  fi
done

# ==================================================
# 5. Clean empty directories
# ==================================================
echo "🧹 Cleaning empty directories..."
for MODULE in "${MODULES[@]}"; do
  if [ -d "$MODULE/src" ]; then
    find "$MODULE/src" -type d -empty -delete
  fi
done

# ==================================================
# DONE
# ==================================================
echo "✅ Project initialized successfully!"
echo ""
echo "👉 Recommended next steps:"
echo "   - Sync Gradle"
echo "   - Verify namespaces per module"
echo "   - Update Firebase / CI / signing configs"
echo "   - Commit initial setup"
