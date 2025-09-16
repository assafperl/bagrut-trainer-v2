#!/usr/bin/env bash
set -euo pipefail

QID="Q3_2024_381"
PART="${1:-ALL}"             # אפשר A / B / ALL
OUTDIR="server/submissions/$QID"
mkdir -p "$OUTDIR"

ts=$(date -u +%Y%m%d%H%M%S)
rand=$(LC_ALL=C tr -dc 'a-z0-9' </dev/urandom | head -c 6 || echo xxxxxx)
FILE="$OUTDIR/${ts}_${rand}.json"

# קומפילציה
if ! javac -encoding UTF-8 Q3_2024_381.java Grader.java 2>/tmp/javac.err; then
  err_b64=$(base64 </tmp/javac.err | tr -d '\n' || true)
  printf '{"qid":"%s","part":"%s","status":"COMPILE_ERROR","error_b64":"%s","savedAt":"%s"}\n' \
    "$QID" "$PART" "$err_b64" "$(date -u +%FT%TZ)" | tee "$FILE" >/dev/null
  echo "❌ COMPILE_ERROR — נשמר: $FILE"
  exit 1
fi

# הרצה (Grader מוציא JSON ל-stdout)
if ! java Grader "$PART" | tee "$FILE" >/dev/null; then
  echo "❌ Runtime error — נשמר: $FILE"
  exit 1
fi

echo "✅ נשמר לוג: $FILE"
