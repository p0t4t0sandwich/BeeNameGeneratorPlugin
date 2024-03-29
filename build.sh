#!/bin/bash

PROJ_ID=beenamegenerator
PROJ_NAME=BeeNameGenerator
VERSION=1.0.1
GROUP_ID=dev/neuralnexus

# --------------------------- Functions --------------------------------

function prepareFiles() {
  echo "Preparing files for $1"

  cp ../$PROJ_NAME-$1-$VERSION.jar ./
  mv ./$PROJ_NAME-$1-$VERSION.jar ./$PROJ_NAME-$1-$VERSION.zip
  unzip -q ./$PROJ_NAME-$1-$VERSION.zip -d ./$1
  rm -rf ./$PROJ_NAME-$1-$VERSION.zip
}

function spongebuild() {
  echo "Building using Forge $2, Fabric $1 and Sponge $3"

  mkdir -p ./$4
  mkdir -p ./$4/META-INF

  # Copy common files
  cp -r ./$PROJ_NAME-all/* ./$4/

  # Copy fabric files
  cp -r ./fabric-$1/$GROUP_ID/$PROJ_ID/fabric ./$4/$GROUP_ID/$PROJ_ID
  cp ./fabric-$1/fabric.mod.json ./$4
  cp ./fabric-$1/$PROJ_ID.mixins.json ./$4
  cp -r ./fabric-$1/assets ./$4
  cp ./fabric-$1/fabric-$1-refmap.json ./$4
  cp -r ./fabric-$1/META-INF/jars ./$4/META-INF

  # Copy forge files
  cp -r ./forge-$2/$GROUP_ID/$PROJ_ID/forge ./$4/$GROUP_ID/$PROJ_ID
  cp ./forge-$2/pack.mcmeta ./$4
  cp -r ./forge-$2/$PROJ_NAME.png ./$4
  cp ./forge-$2/META-INF/mods.toml ./$4/META-INF
  cp ./forge-$2/mcmod.info ./$4

  # Copy sponge files
  cp -r ./sponge$3/$GROUP_ID/$PROJ_ID/sponge ./$4/$GROUP_ID/$PROJ_ID
  cp ./sponge$3/META-INF/sponge_plugins.json ./$4/META-INF

  # Zip Jar contents
  cd ./$4
  zip -qr ../$4.zip ./*
  cd ../

  # Rename Jar
  mv ./$4.zip ./$4.jar

  # Generate hashes
  md5sum ./$4.jar | cut -d ' ' -f 1 > ./$4.jar.md5
  mv ./$4.jar.md5 ../$4.jar.md5
  sha1sum ./$4.jar | cut -d ' ' -f 1 > ./$4.jar.sha1
  mv ./$4.jar.sha1 ../$4.jar.sha1
  sha256sum ./$4.jar | cut -d ' ' -f 1 > ./$4.jar.sha256
  mv ./$4.jar.sha256 ../$4.jar.sha256
  sha512sum ./$4.jar | cut -d ' ' -f 1 > ./$4.jar.sha512
  mv ./$4.jar.sha512 ../$4.jar.sha512

  # Move Jar
  mv ./$4.jar ../$4.jar
}

# --------------------------- Setup --------------------------------

# Make directories
mkdir -p ./target/temp_build
cd ./target/temp_build

mkdir -p ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID

# --------------------------- Prepare Common --------------------------------

# Prepare bukkit files
prepareFiles bukkit

# Copy bukkit files
mv ./bukkit/$GROUP_ID/$PROJ_ID/bukkit ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
cp ./bukkit/plugin.yml ./$PROJ_NAME-all
rm -rf ./bukkit

# Prepare common files
prepareFiles common

# Copy common files
mv ./common/$GROUP_ID/$PROJ_ID/common ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
mv ./common/$GROUP_ID/$PROJ_ID/lib ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
cp ./common/$PROJ_ID.config.yml ./$PROJ_NAME-all
cp ../../LICENSE ./$PROJ_NAME-all
cp ../../LICENSE-API ./$PROJ_NAME-all
cp ../../README.md ./$PROJ_NAME-all
rm -rf ./common

# --------------------------- Prepare Sponge --------------------------------

SPONGE_VERSIONS=(8)
for SPONGE_VERSION in "${SPONGE_VERSIONS[@]}"
do
    prepareFiles sponge$SPONGE_VERSION
done

# --------------------------- Prepare Fabric --------------------------------

FABRIC_VERSIONS=(1.15 1.17 1.20)
for FABRIC_VERSION in "${FABRIC_VERSIONS[@]}"
do
    prepareFiles fabric-$FABRIC_VERSION
done

# --------------------------- Prepare Forge --------------------------------

FORGE_VERSIONS=(1.15.1 1.16.3 1.17.1 1.18 1.19 1.20)
for FORGE_VERSION in "${FORGE_VERSIONS[@]}"
do
    prepareFiles forge-$FORGE_VERSION
done

# --------------------------- Prepare NeoForge --------------------------------

NEOFORGE_VERSIONS=(1.20.1)
for NEOFORGE_VERSION in "${NEOFORGE_VERSIONS[@]}"
do
    prepareFiles neoforge-$NEOFORGE_VERSION
done

# --------------------------- Build 1.15 --------------------------------
MC_VERSION=1.15
FABRIC_VERSION=1.15
FORGE_VERSION=1.15.1
SPONGE_VERSION=8
OUT_FILE=$PROJ_NAME-$MC_VERSION-$VERSION
spongebuild $FABRIC_VERSION $FORGE_VERSION $SPONGE_VERSION $OUT_FILE

# --------------------------- Build 1.16 --------------------------------
MC_VERSION=1.16
FABRIC_VERSION=1.15
FORGE_VERSION=1.16.3
SPONGE_VERSION=8
OUT_FILE=$PROJ_NAME-$MC_VERSION-$VERSION
spongebuild $FABRIC_VERSION $FORGE_VERSION $SPONGE_VERSION $OUT_FILE

# --------------------------- Build 1.17 --------------------------------
MC_VERSION=1.17
FABRIC_VERSION=1.17
FORGE_VERSION=1.17.1
SPONGE_VERSION=8
OUT_FILE=$PROJ_NAME-$MC_VERSION-$VERSION
spongebuild $FABRIC_VERSION $FORGE_VERSION $SPONGE_VERSION $OUT_FILE

# --------------------------- Build 1.18 --------------------------------
MC_VERSION=1.18
FABRIC_VERSION=1.17
FORGE_VERSION=1.18
SPONGE_VERSION=8
OUT_FILE=$PROJ_NAME-$MC_VERSION-$VERSION
spongebuild $FABRIC_VERSION $FORGE_VERSION $SPONGE_VERSION $OUT_FILE

# --------------------------- Build 1.19 --------------------------------
MC_VERSION=1.19
FABRIC_VERSION=1.17
FORGE_VERSION=1.19
SPONGE_VERSION=8
OUT_FILE=$PROJ_NAME-$MC_VERSION-$VERSION
spongebuild $FABRIC_VERSION $FORGE_VERSION $SPONGE_VERSION $OUT_FILE

# --------------------------- Build 1.20 --------------------------------
MC_VERSION=1.20
FABRIC_VERSION=1.20
FORGE_VERSION=1.20
SPONGE_VERSION=8
OUT_FILE=$PROJ_NAME-$MC_VERSION-$VERSION
spongebuild $FABRIC_VERSION $FORGE_VERSION $SPONGE_VERSION $OUT_FILE

# --------------------------- Cleanup --------------------------------
cd ../
rm -rf temp_build
