🚧 This project is still under heavy development. 🚧

---

# DiskWalker

Desktop application for viewing disk space usage.

## Screenshots

|                                                 Light Theme                                                  |                                                 Dark Theme                                                  |
|:------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------:|
| ![dashboard-light](https://github.com/lukwol/diskwalker/assets/2930137/b08ecaf8-92d9-47c0-8f0a-8a1a85e80123) | ![dashboard-dark](https://github.com/lukwol/diskwalker/assets/2930137/573f9720-a81c-495a-a339-d3a45f3663e4) |
|   ![scan-light](https://github.com/lukwol/diskwalker/assets/2930137/3f04bf57-4b4c-474b-88c2-158b9ced8f01)    |   ![scan-dark](https://github.com/lukwol/diskwalker/assets/2930137/d9164e0c-b68f-4aaf-b594-3cc32d082658)    |
|   ![chart-light](https://github.com/lukwol/diskwalker/assets/2930137/ddc1fdab-51b2-4da1-b86f-076867595542)   |   ![chart-dark](https://github.com/lukwol/diskwalker/assets/2930137/2d80d5f1-e06d-4fa4-849a-347b3364602b)   |

## Building Prerequisites

Since some libraries have not been published to Maven Central yet,
it's required to build and publish them to local Maven repository (at least JVM target).

Checkout following repositories and publish libraries with proper version that can be found in
[libs.versions.toml](https://github.com/lukwol/diskwalker/blob/master/diskwalker/gradle/libs.versions.toml).

[cm-navigation](https://github.com/lukwol/cm-navigation):

* viewmodel
* screens-navigation
* viewmodel-screens-navigation

```shell
# Publish all targets
./gradlew viewmodel:publishToMavenLocal \
    screens-navigation:publishToMavenLocal \
    viewmodel-screens-navigation:publishToMavenLocal

# Or publish only JVM target
./gradlew viewmodel:publishJvmPublicationToMavenLocal \
    screens-navigation:publishJvmPublicationToMavenLocal \
    viewmodel-screens-navigation:publishJvmPublicationToMavenLocal
```

[async](https://github.com/Anvell/async):

* async
* async-state

```shell
# Publish all targets
./gradlew async:publishToMavenLocal \
    async-state:publishToMavenLocal

# Or publish only JVM target
./gradlew async:publishJvmPublicationToMavenLocal \
    async-state:publishJvmPublicationToMavenLocal
```
