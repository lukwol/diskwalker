# disk-usage

Desktop application for viewing disk space usage.

Built with [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/).

```
             ┌───────────────┐
       ┌─────┤ VM Navigation ├──────┐
       │     └───────────────┘      │
       │                            │
       │ depends on                 │ depends on
       │                            │
┌──────▼─────┐               ┌──────▼─────┐
│ View Model │               │ Navigation │
└────────────┘               └────────────┘
```
