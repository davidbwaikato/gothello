#!/bin/bash

# Work out where we are on the filesystem:
#   https://stackoverflow.com/questions/59895/how-can-i-get-the-source-directory-of-a-bash-script-from-within-the-script-itsel

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
GOTHELLO_SERVER_HOME=${SCRIPT_DIR%/*}

(cd "$GOTHELLO_SERVER_HOME" && ./gothello-start-server.sh )&

