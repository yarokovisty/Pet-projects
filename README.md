# Rick and Morty

A multiplatform application for browsing characters, episodes, and locations from the Rick and Morty universe using the Rick and Morty API.

## Purpose

The application provides access to structured data from the Rick and Morty API, enabling retrieval and display of characters, episodes, and locations across multiple platforms. The application addresses the requirement for a unified interface to consume and present data from the Rick and Morty API endpoints.

## Scope

The application is designed for execution on Android, iOS, and JVM desktop platforms. The system supports listing characters with filtering capabilities, viewing character details, browsing episodes grouped by seasons, and exploring locations with associated residents.

Out of scope: offline data persistence, user authentication, content modification capabilities, and real-time data synchronization.

## Features

- Character browsing with pagination support
- Character filtering by name, status, and gender
- Character detail view with associated episode information
- Episode listing grouped by season
- Location browsing with resident information
- Multiplatform support (Android, iOS, Desktop JVM)
- Type-safe navigation with KSP-generated navigators
- Asynchronous image loading with caching

## Architecture

The application follows Clean Architecture principles with a modular structure. The system is organized into distinct layers: presentation, domain, and data.

### Core Components

- **Presentation Layer**: Compose Multiplatform UI components and ViewModels following MVI pattern. Each ViewModel extends BaseViewModel with State, Intent, and Event management.
- **Domain Layer**: Use cases encapsulating business logic and repository interfaces defining data contracts.
- **Data Layer**: Repository implementations and remote data sources responsible for API communication.
- **Core Modules**: Shared functionality including network client configuration, navigation framework, coroutine dispatchers, and result handling.
- **Design System**: Reusable UI components, theme definitions, and resource management.
- **Library Modules**: Custom navigation annotation processor (KSP), logging utilities, and result wrapper types.

### Modular Structure

The project uses a feature-based modular architecture. Each feature is split into API and implementation modules:

- **feature:character**: Character browsing, filtering, and detail views
- **feature:episode**: Episode listing and character episode associations
- **feature:location**: Location browsing with resident information

Common functionality is extracted into:

- **common:data**: Shared data layer abstractions
- **common:presentation**: BaseViewModel and MVI infrastructure
- **core:network**: Ktor HTTP client configuration and safe request wrappers
- **core:navigation**: Navigation abstractions and type-safe routing
- **core:dispatchers**: Coroutine dispatcher providers
- **design**: UI components, theme, and resources

The shared module aggregates all feature and core modules for iOS framework export.

### MVI Pattern

State management follows the Model-View-Intent pattern. ViewModels expose StateFlow for UI state, process user intents through onIntent function, and emit one-time events via SharedFlow. All ViewModels extend BaseViewModel which provides structured state updates and event emission mechanisms.

## Technologies

![Kotlin 2.2.20](https://img.shields.io/badge/Kotlin-2.2.20-blue)
![Kotlin Multiplatform 2.2.20](https://img.shields.io/badge/Kotlin%20Multiplatform-2.2.20-blue)
![Compose Multiplatform 1.9.1](https://img.shields.io/badge/Compose%20Multiplatform-1.9.1-blue)
![Compose Compiler 2.2.20](https://img.shields.io/badge/Compose%20Compiler-2.2.20-blue)
![Android Gradle Plugin 8.13.1](https://img.shields.io/badge/Android%20Gradle%20Plugin-8.13.1-blue)
![KSP 2.2.20-2.0.4](https://img.shields.io/badge/KSP-2.2.20--2.0.4-blue)
![Ktor Client 3.3.1](https://img.shields.io/badge/Ktor%20Client-3.3.1-blue)
![Kotlinx Coroutines 1.10.2](https://img.shields.io/badge/Kotlinx%20Coroutines-1.10.2-blue)
![Kotlinx Serialization 1.9.0](https://img.shields.io/badge/Kotlinx%20Serialization-1.9.0-blue)
![Koin 4.1.1](https://img.shields.io/badge/Koin-4.1.1-blue)
![Lifecycle 2.9.3](https://img.shields.io/badge/Lifecycle-2.9.3-blue)
![Navigation Compose 2.9.1](https://img.shields.io/badge/Navigation%20Compose-2.9.1-blue)
![Coil 3.3.0](https://img.shields.io/badge/Coil-3.3.0-blue)

## Project Structure

```
RickAndMorty/
├── composeApp/              # Main application module
│   └── src/
│       ├── androidMain/     # Android-specific code
│       ├── iosMain/         # iOS-specific code
│       ├── jvmMain/         # Desktop (JVM) specific code
│       └── commonMain/      # Shared code across all platforms
├── shared/                  # Aggregates all modules for iOS framework
├── common/
│   ├── data/               # Shared data abstractions
│   └── presentation/       # BaseViewModel and MVI infrastructure
├── core/
│   ├── dispatchers/        # Coroutine dispatchers
│   ├── navigation/         # Navigation framework
│   └── network/            # Ktor HTTP client configuration
├── design/
│   ├── component/          # Reusable UI components
│   ├── resources/          # Assets and resources
│   └── theme/              # Theme definitions
├── feature/
│   ├── character/
│   │   ├── api/            # Public API and domain contracts
│   │   └── impl/           # Implementation module
│   ├── episode/
│   │   ├── api/
│   │   └── impl/
│   └── location/
│       ├── api/
│       └── impl/
├── library/
│   ├── logger/             # Logging utilities
│   ├── navigation-ksp/     # Navigation code generation
│   │   ├── annotation/     # KSP annotations
│   │   └── processor/      # KSP processor
│   └── result/             # Result wrapper types
└── util/                   # Utility functions
```