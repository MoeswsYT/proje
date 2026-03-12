#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
APK_PATH="$ROOT_DIR/app/build/outputs/apk/debug/app-debug.apk"

if ! command -v adb >/dev/null 2>&1; then
  echo "[HATA] adb bulunamadı. Android SDK Platform-Tools kurun." >&2
  exit 1
fi

if [[ ! -f "$APK_PATH" ]]; then
  echo "[HATA] APK bulunamadı: $APK_PATH" >&2
  echo "Önce scripts/build_apk.sh çalıştırın." >&2
  exit 1
fi

echo "[INFO] Cihaza/emülatöre APK yükleniyor..."
adb install -r "$APK_PATH"

echo "[OK] Kurulum tamamlandı. Uygulamayı cihazdan açabilirsiniz."
