<div align="center">
  <img src="composeApp/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_round.png" width="96" alt="Delivery app icon">

  # Delivery

  *A Kotlin Multiplatform delivery management app for Android, iOS, and Desktop*

  [![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-7F52FF?style=flat-square&logo=kotlin)](https://kotlinlang.org)
  [![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.10.2-4285F4?style=flat-square&logo=jetpackcompose)](https://www.jetbrains.com/compose-multiplatform/)
  [![Android SDK](https://img.shields.io/badge/Android-API_24%2B-green?style=flat-square&logo=android)](https://developer.android.com)
  [![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)

</div>

Delivery is a cross-platform courier management application built with Kotlin Multiplatform and Compose Multiplatform. It allows couriers and logistics personnel to calculate delivery costs, manage orders, and track their shipment history — all from a single shared codebase running on Android, iOS, and JVM Desktop.

## Features

- **OTP Authentication** — Phone-based sign-in with one-time password verification
- **Delivery Calculator** — Compute delivery costs based on routes, parcels, payers, and persons
- **Order Management** — Create and confirm orders with full sender/recipient details and delivery points
- **Order History** — Browse past orders with detailed per-order breakdown
- **Profile** — View and manage the logged-in user's profile
- **Multiplatform** — Shared business logic and UI across Android, iOS (arm64/x64/simulator), and JVM Desktop

## Architecture

The app follows a strict **MVI (Model-View-Intent)** pattern with unidirectional data flow, layered on top of Clean Architecture principles.

```
┌─────────────────────────────────────────────────┐
│  Presentation (Compose UI + ViewModel)           │
│  State ← StateFlow   Intent → onIntent()        │
│  Event ← SharedFlow (one-shot side effects)     │
├─────────────────────────────────────────────────┤
│  Domain (Repository interfaces, Use cases)       │
├─────────────────────────────────────────────────┤
│  Data (Ktor API, DTOs, Repository implementations│
└─────────────────────────────────────────────────┘
```

All ViewModels extend `BaseViewModel<S : State, I : Intent, E : Event>`. State is immutable; views only collect state and dispatch intents — no business logic in composables.

### Module Structure

The project is organized into ~46 Gradle modules across seven layers:

| Layer | Modules | Purpose |
|-------|---------|---------|
| `composeApp` | 1 | App shell, navigation, DI initialization |
| `feature/` | 11 | Screen-level features (login, delivery, history, profile) |
| `common/` | 18 | Shared domain models, repository interfaces, use cases |
| `core/` | 5 | Infrastructure (network, storage, coroutines, base ViewModel) |
| `design/` | 3 | Theme, UIKit components, resources |
| `libs/` | 3 | Navigation wrapper, coordinator, encryption |
| `util/` | 8 | Coroutine, Flow, phone, validation, and Modifier extensions |

Each feature module is split into an `api` module (public contracts) and an `impl` module (internal implementation).

## Tech Stack

| Category | Library | Version |
|----------|---------|---------|
| Language | Kotlin | 2.3.0 |
| UI | Compose Multiplatform | 1.10.2 |
| Networking | Ktor | 3.3.1 |
| Dependency Injection | Koin | 4.2.0 |
| Serialization | kotlinx-serialization | 1.8.0 |
| Concurrency | kotlinx-coroutines | 1.10.2 |
| Navigation | Compose Navigation3 | — |
| Persistence | AndroidX DataStore | 1.1.7 |
| Encryption | cryptography-kotlin | 0.6.0 |
| Linting | Detekt | 1.23.8 |
| Testing | JUnit 4 + MockK | — |

## Getting Started

### Prerequisites

- JDK 11 or higher
- Android Studio Hedgehog or later (for Android targets)
- Xcode 15+ (for iOS targets, macOS only)
- Kotlin Multiplatform plugin

### Build

```bash
# Build all targets
./gradlew build

# Compile only (faster syntax check)
./gradlew compileKotlin

# Run Detekt static analysis
./gradlew detekt

# Run unit tests
./gradlew test
```

> [!NOTE]
> Detekt is configured with `maxIssues=0` — the build will fail on any lint violation. Run `./gradlew detekt` before pushing.

### Run on Android

Open the project in Android Studio and run the `composeApp` configuration on an emulator or physical device (API 24+).

### Run on iOS

```bash
# Open the Xcode project
open iosApp/iosApp.xcodeproj
```

Build and run from Xcode targeting a simulator or physical device.

## Project Structure

```
Delivery/
├── build-logic/            # Convention plugins (kmp, androidLibrary)
├── composeApp/             # App entry point, navigation, DI wiring
├── common/                 # Shared domain models and use cases
│   ├── auth/
│   ├── delivery/{calculator,direction,order,parcel,payer,person,point,step}/
│   ├── logout/
│   ├── profile/
│   └── validation/
├── core/
│   ├── common/{coroutines,error,presentation}/
│   ├── network/            # Ktor client (OkHttp/Darwin engines)
│   └── storage/            # DataStore persistence
├── design/
│   ├── resources/
│   ├── theme/              # Material3 DeliveryTheme
│   └── uikit/              # Reusable Compose components
├── feature/
│   ├── delivery/{main,calculator,direction,order,payer,person,point}/
│   ├── history/{main,details}/
│   ├── login/
│   └── profile/main/
├── libs/
│   ├── coordinator/
│   ├── encryption/
│   └── navigation/
└── util/                   # Coroutines, Flow, phone, validation helpers
```

## Code Style

The project enforces code quality via Detekt. Key rules:

- Max line length: **120 characters**
- Max function length: **60 lines**, max return count: **2**
- No `TODO`, `FIXME`, or `STOPSHIP` comments
- Magic numbers forbidden (except -1, 0, 1, 2)
- No wildcard imports
- `internal` visibility required inside `impl` modules
- Dispatchers must be injected — never referenced directly

> [!IMPORTANT]
> New modules must apply the `org.yarokovisty.delivery.kmp` or `org.yarokovisty.delivery.androidLibrary` convention plugin instead of configuring targets manually. Register the module in `modules.gradle.kts`.
