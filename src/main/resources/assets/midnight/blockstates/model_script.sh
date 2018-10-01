#!/bin/sh

cd "$(dirname $0)"

test -f "$1.json" && exit 1
printf '{
  "forge_marker": 1,
  "defaults": {
    "textures": {
      "all": "midnight:blocks/%s"
    }
  },
  "variants": {
    "normal": {
      "model": "cube_all"
    },
    "inventory": {
      "model": "cube_all"
    }
  }
}' "$1" > "$1.json"