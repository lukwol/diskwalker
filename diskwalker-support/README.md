# diskwalker-support

---

Generating FFI header:

```shell
javac -h . SupportJni.java
```

Adding targets:

```shell
rustup target add \
    x86_64-apple-darwin \
    aarch64-apple-darwin \
    x86_64-pc-windows-gnu
```

Building:

```shell
cargo build --release

cp diskwalker-support/target/aarch64-apple-darwin/release/libsupport.dylib diskwalker/app/src/main/resources/macos-arm64/libsupport.dylib
cp diskwalker-support/target/x86_64-apple-darwin/release/libsupport.dylib diskwalker/app/src/main/resources/macos-x64/libsupport.dylib
cp diskwalker-support/target/x86_64-pc-windows-gnu/release/support.dll diskwalker/app/src/main/resources/windows/support.dll
```
