#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

if [[ ! -f "app/google-services.json" ]]; then
  echo "[HATA] app/google-services.json bulunamadı. Firebase'den indirip app/ altına koyun." >&2
  exit 1
fi

if [[ -z "${JAVA_HOME:-}" ]]; then
  if [[ -d "/root/.local/share/mise/installs/java/17.0.2" ]]; then
    export JAVA_HOME="/root/.local/share/mise/installs/java/17.0.2"
    export PATH="$JAVA_HOME/bin:$PATH"
  else
    echo "[HATA] JAVA_HOME ayarlı değil. JDK 17 ile JAVA_HOME tanımlayın." >&2
    exit 1
  fi
fi

if ! java -version 2>&1 | head -n 1 | grep -q '"17\.'; then
  echo "[UYARI] JDK 17 önerilir. Mevcut sürüm:" >&2
  java -version >&2
fi

echo "[INFO] Debug APK üretiliyor..."
gradle :app:assembleDebug --no-daemon

echo "[OK] APK hazır: $ROOT_DIR/app/build/outputs/apk/debug/app-debug.apk"
