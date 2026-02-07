#!/bin/bash

# ==================================================
# INPUT
# ==================================================
OLD_PACKAGE=$1
NEW_APP_NAME=$2
NEW_PACKAGE=$3

if [ -z "$NEW_APP_NAME" ] || [ -z "$OLD_PACKAGE" ] || [ -z "$NEW_PACKAGE" ]; then
  echo "❌ Usage:"
  echo "   ./init-project.sh \"My App\" com.old.package com.new.package"
  exit 1
fi

echo "🚀 Initializing Android project..."
echo "App Name     : $NEW_APP_NAME"
echo "Old Package  : $OLD_PACKAGE"
echo "New Package  : $NEW_PACKAGE"

# ==================================================
# PATH HELPERS
# ==================================================
OLD_PATH=$(echo $OLD_PACKAGE | tr '.' '/')
NEW_PATH=$(echo $NEW_PACKAGE | tr '.' '/')

# ==================================================
# 1. Replace package references
# ==================================================
echo "🔁 Replacing package references..."

grep -rl "$OLD_PACKAGE" app | xargs sed -i '' "s/$OLD_PACKAGE/$NEW_PACKAGE/g"

# ==================================================
# 2. Rename directory structure
# ==================================================
echo "📁 Updating folder structure..."

for SRC in app/src/main/java app/src/test/java app/src/androidTest/java; do
  if [ -d "$SRC/$OLD_PATH" ]; then
    mkdir -p "$SRC/$(dirname $NEW_PATH)"
    mv "$SRC/$OLD_PATH" "$SRC/$NEW_PATH"
  fi
done

# ==================================================
# 3. Update app name
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

grep -rl "$OLD_PACKAGE" app/build.gradle* | xargs sed -i '' "s/$OLD_PACKAGE/$NEW_PACKAGE/g"

# ==================================================
# 5. Clean empty directories
# ==================================================
echo "🧹 Cleaning empty directories..."
find app/src -type d -empty -delete

# ==================================================
# DONE
# ==================================================
echo "✅ Project initialized successfully!"
echo "👉 Next steps:"
echo "   - Sync Gradle"
echo "   - Update Firebase / CI configs"
echo "   - Commit initial project setup"