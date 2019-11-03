#!/bin/bash
set -e
if ["$TARGET" = "lab6"]; then
    if ["$OSTYPE" = "darwin17"]; then
        wget https://github.com/forax/java-next/releases/download/untagged-c59655314c1759142c15/jdk-14-loom-osx.tar.gz
        tar xvf jdk-14-loom-osx.tar.gz
    else
        wget https://github.com/forax/java-next/releases/download/untagged-c59655314c1759142c15/jdk-14-loom-linux.tar.gz
        tar xvf jdk-14-loom-linux.tar.gz
    fi
    export JAVA_HOME=$(pwd)/jdk-14-loom/
fi
cd $TARGET
mvn clean package
