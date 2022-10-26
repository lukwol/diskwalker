# disk-usage

Desktop application for viewing disk space usage.

Built with [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/).

```
                      ┌──────────────────────────────┐
          ┌───────────┤ viewmodel-screens-navigation ├───────────┐
          │           └──────────────────────────────┘           │
          │                                                      │
          │ depends on                                depends on │
          │                                                      │
          │                                                      │
┌─────────▼──────────┐       ┌────────────────────┐        ┌─────▼─────┐
│ screens-navigation │       │ windows-navigation │        │ viewmodel │
└────────────────────┘       └────────────────────┘        └───────────┘
```
