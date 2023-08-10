#!/bin/bash
cp diskwalker-support/target/aarch64-apple-darwin/release/libsupport.dylib diskwalker/app/src/main/resources/macos-arm64/libsupport.dylib
cp diskwalker-support/target/x86_64-apple-darwin/release/libsupport.dylib diskwalker/app/src/main/resources/macos-x64/libsupport.dylib
cp diskwalker-support/target/x86_64-pc-windows-gnu/release/support.dll diskwalker/app/src/main/resources/windows/support.dll