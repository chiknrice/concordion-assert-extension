#!/usr/bin/env bash

set -o errexit -o nounset

rm -rf gh-pages

mkdir gh-pages

cd gh-pages

git clone --depth=50 --branch=gh-pages https://$GH_TOKEN@github.com/chiknrice/concordion-assert-extension.git

if [[ -n "${TRAVIS_TAG-}" ]]
then
    echo "Adding concordion specs for $TRAVIS_TAG"
    mkdir concordion-assert-extension/$TRAVIS_TAG
    cp -R ../build/reports/spec/* concordion-assert-extension/$TRAVIS_TAG
fi

rm -rf concordion-assert-extension/latest
mkdir concordion-assert-extension/latest
cp -R ../build/reports/spec/* concordion-assert-extension/latest

cd concordion-assert-extension

echo Testing git status -s
git status -s

if [[ -n $(git status -s) ]]
then
    echo "Updating latest concordion specs"

    git config user.email "chiknrice@gmail.com"
    git config user.name "Travis CI"

    git add .
    git commit -m "Update concordion spec result"

    git push
fi
