#!/usr/bin/env bash

set -o errexit -o nounset

rm -rf gh-pages

mkdir gh-pages

cd gh-pages

git clone --depth=50 --branch=gh-pages https://$GH_TOKEN@github.com/chiknrice/concordion-assert-extension.git

VER="${TRAVIS_TAG:-latest}"

if [ $VER == "latest" ];
then
    rm -rf concordion-assert-extension/$VER
fi

mkdir concordion-assert-extension/$VER

cp -R ../build/reports/spec/* concordion-assert-extension/$VER

cd concordion-assert-extension

git config user.email "chiknrice@gmail.com"
git config user.name "Travis CI"

git add .
git commit -m "Update concordion spec result"

git push
