# AudioQuiz

![](asset/header.jpeg)


## Hilt & Modularization
This Android project implements the Model-View-Intent architecture using Java, Dagger-Hilt, and multi-module structure.

## Project Structure~~~~

This repository demonstrates an online shopping app that follows best practices for Android development. The app is built using the MVI architecture, Domain use cases, Hilt, single activity architecture, and flow. There are 8 modules in this project:

- `app`: This module is the entry point of the Android app and contains only a splash screen.
- `account`: This module is for all the screens that need the user to be authenticated, such as the dashboard, home, user profile, settings, cart, etc.
- `login`: This module is for user authentication flow, such as login, signup and forgot password.
- `home`: This module is for the home screen(s)
- `stats`: This module is user statistics screen
- `rank`: This module is user ranking screens
- `settings`: This module is for user settings
- `core`: This module contains domain model, interface contracts, base classes, shared classes, and utils.
- `navigation`: This module contains all the base classes needed for navigation coordinator pattern.
- `style`: This module has all the shared resources among the other modules, such as styles, colors, theme, drawable, etc.
- `ui-components`: This module contains all the custom UI components to be used by other modules, such as CustomButton, CustomProgressIndicator, etc.

The project is structured using the multi-module architecture approach, which separates different functionalities into individual modules. The following is a brief overview of each module:
- `data` package: The classes inside this package that handles data retrieval from different sources, such as APIs or local databases. It uses Retrofit and Room libraries to implement the data layer.
- `domain` package: The classes inside this package that contains the business logic and domain models of the app. It provides the use cases and repositories that are used by the presentation layer.
- `presentation` package: The classes inside this package that handles the UI and user interactions. It implements the MVI architecture using Coroutine flow and provides the view models and view states that are used by the app.
- `di` package: The classes inside this package that contains all the dagger modules and components which is needed in the module.
- `navigation` package: The classes inside this package that contains navigation flow coordinator and navigation effect to break the dependency of ui and navigation components. This help for a clearer structure and also better unit testing the navigation directions.
-
## Notes MVVM+LiveData+MVI
- ViewState:
    -     As the name suggests this is part of the model layer, and our view observes this model for state changes. 
            ViewState should represent the current state of the view at any given time. 
            So this class should have all the variable content on which our view is dependent. 
            Every time there is any user input/event we will expose modified copy (to maintain the previous state which is not being modified) of this class.

- ViewEffect:
    -     In Android, we have certain actions that are more like fire-and-forget, for example- Toast, in those cases,
             we can not use ViewState as it maintains state. It means, if we use ViewState to show a Toast, 
           it will be shown again on configuration change or every time there is a new state unless and until we reset its state by passing ‘toast is shown’ effect. 
           And if you do not wish to do that, you can use ViewEffect as it is based on SingleLiveEvent and does not maintain state.

- ViewEvent:
    -     It represents all actions/events a user can perform on the view. This is used to pass user input/event to the ViewModel.

# Base Principles
# Core Principles
- Separation of Concerns: Divide your app into distinct layers with clear responsibilities:
  -- UI Layer (presentation)
  -- Data Layer (business logic)
  -- Domain Layer (optional - complex/reusable logic)
- Avoid putting business logic in UI classes like Activities and Fragments.
- Drive UI from Data Models: Use data models, ideally persistent ones (e.g., database-backed), to represent your app's data. This ensures data survives configuration changes and works offline.
- Single Source of Truth (SSOT): Each data type has a single owner responsible for modifications. This improves data consistency and debugging.
- Unidirectional Data Flow (UDF): Data flows in one direction (typically from data layer to UI), while events flow in the opposite direction. This enhances data consistency and reduces errors.
# Recommended Architecture
## UI Layer
- Displays data using Views or Jetpack Compose.
- Uses state holders (e.g., ViewModels) to hold and manage UI data.
## Data Layer
- Contains business logic and exposes data through repositories.
- Repositories interact with data sources (database, network, etc.).
## Domain Layer (Optional)
- Encapsulates complex or reusable business logic in use cases or interactors.
- Simplifies interactions between UI and data layers.
# Additional Best Practices
- Dependency Injection: Use DI (e.g., Hilt) to manage dependencies between classes.
- Avoid Data in App Components: Don't store data in Activity, Service, etc., as they have short lifecycles.
- Minimize Android Dependencies: Abstract away from Android classes (e.g., Context) for better testability.
- Well-Defined Responsibilities: Create modules with clear boundaries and avoid mixing unrelated logic.
- Testability: Design for testability by isolating modules and using well-defined APIs.
- Concurrency: Each type manages its own concurrency (e.g., moving long-running tasks off the main thread).
- Offline Support: Persist data locally to provide functionality even without a network connection.

## Flows
- Login flow: The auth module represents it when user needs to be authenticated
- Home flow: The account module represents it when user is authenticated and gets access to the home screen
- User registration flow: The onboarding module represents it when user needs to be registered


# Database Structure
users-data  (Main collection containing the data of all users with one document per user)
|
├── $userId (Document)  (Document ID is the user's ID)
|     |
|     ├── Fields (UserProfile) 
|     |     ├── lastUpdated: long
|     |     ├── userId: string
|     |     ├── username: string
|     |     ├── profileImage: string
|     |     └── dateCreated: long
|     |
|     ├── SUB-COLLECTION: general_stats
|     |     |
|     |     └── $documentId (Document)
|     |           ├── Fields (GeneralStats)
|     |           |     ├── id: string
|     |           |     ├── lastUpdated: Date
|     |           |     ├── userLevel: int
|     |           |     ├── numberOfLives: int
|     |           |     ├── numberOfQuizzes: int
|     |           |     ├── numberOfQuestions: int
|     |           |     ├── totalScore: int
|     |           |     ├── averageScore: double
|     |           |     ├── currentStreak: int
|     |           |     └── longestStreak: int
|     |           └── lastQuizDate: Date
|     |
|     ├── SUB-COLLECTION: category_stats
|     |     |
|     |     └── $documentId (Document)
|     |           ├── Fields (CategoryStats)
|     |           |     ├── id: string
|     |           |     ├── lastUpdated: Date
|     |           |     ├── soundWavesStats: CategoryStatsData
|     |           |     ├── synthesisStats: CategoryStatsData
|     |           |     ├── productionStats: CategoryStatsData
|     |           |     ├── mixingStats: CategoryStatsData
|     |           |     ├── processingStats: CategoryStatsData
|     |           |     ├── musicTheoryStats: CategoryStatsData
|     |           |     ├── pitchStats: CategoryStatsData
|     |           |     └── intervalStats: CategoryStatsData
|     |           |     
|     |           ├── Fields (CategoryStatsData)
|     |           |     ├── categoryIndex: int
|     |           |     ├── categoryName: string
|     |           |     ├── lastUpdated: Date
|     |           |     ├── currentChapter: int
|     |           |     ├── numberOfQuizzes: int
|     |           |     ├── totalQuestionsLearn: int
|     |           |     ├── correctAnswersLearn: int
|     |           |     ├── totalQuestionsCompetitive: int
|     |           |     └── correctAnswersCompetitive: int
|     |           └── totalTimeSpent: double
|     |
|     ├── SUB-COLLECTION: frequency_stats
|     |     |
|     |     └── $documentId (Document)
|     |           ├── Fields (FrequencyStats)
|     |           |     ├── id: string
|     |           |     ├── lastUpdated: Date
|     |           |     ├── pitchScoresMap: Map<String, PitchStats>
|     |           |     └── intervalScoresMap: Map<String, IntervalStats>
|     |           |     
|     |           ├── Subfields (PitchStats)
|     |           |     ├── id: string
|     |           |     ├── lastUpdated: Date
|     |           |     ├── frequency: string
|     |           |     ├── totalQuestions: int
|     |           |     ├── correctAnswers: int
|     |           |     └── mistakes: Map<String, Integer>
|     |           |     
|     |           └── Subfields (IntervalStats)
|     |                 ├── id: string
|     |                 ├── lastUpdated: Date
|     |                 ├── intervalName: string
|     |                 ├── baseFrequency: int
|     |                 ├── totalQuestions: int
|     |                 └── correctAnswers: int
|     |                 └── mistakes: Map<String, Integer>
|     |
|     └── SUB-COLLECTION: weekly_scores
|           |
|           └── $documentId (Document)
|                 ├── Fields (WeeklyScores)
|                 |     ├── id: string
|                 |     ├── lastUpdated: Date
|                 |     ├── dailyScores: Map<String, Integer>
|                 └── totalLast7Days: int
|
rank-data (collection used to fetch leaderboard data in batches like top 30 performers)
|
├── $userId (Document)
|     |
|     ├── Fields (RankEntry)
|     |     ├── lastUpdated: long
|     |     ├── userId: string
|     |     ├── username: string
|     |     ├── profileImage: string
|     |     ├── totalScore: int
|     |     ├── averageScore: double
|     |     └── weekScore: int (user's score in the last 7 days)
 
 
Modularization Approach:

Package by Layer: Often unsuitable due to large modules and frequent changes.
Package by Feature: Good for maintenance but may face issues with reusability and large, complex modules.
Package by Component: Optimal for UI-heavy projects, supporting reusability and minimizing recompilations."
  

Explanation
di: Contains dependency injection modules.
LoginFragmentModule.java: Provides dependencies for the login feature.
domain: Contains business logic and domain models.
model: Contains domain models like AccessToken, LoggedInUser, etc.
repository: Interface for the repository pattern.
usecase: Contains use cases for user authentication.
presentation: Contains UI-related classes.
navigation: Handles navigation within the login feature.
view: Contains fragments for login and signup.
viewmodel: Contains ViewModels for managing UI-related data.
data: Contains data sources and repositories.
local: Local data sources like AuthCache, UserAuthDao, and AppDatabase.
remote: Remote data sources like AuthDataSource, GoogleSignInDataSource, and UserProfileDataSource.
dto: Data Transfer Objects for remote data sources.
service: Services for authentication and authorization.
repository: Implementation of the repository pattern.
Benefits of This Structure
Separation of Concerns: Each layer (domain, presentation, data) has a clear responsibility, adhering to the Single Responsibility Principle (SRP).
Modularity: Changes in one module (e.g., UI) do not affect other modules (e.g., domain logic), supporting the Open-Closed Principle (OCP).
Testability: Each component can be tested in isolation, improving maintainability and reliability.
Scalability: New features can be added with minimal impact on existing code, making the system scalable.
 

## Root project 'AudioQuiz'
+--- Project ':app'
+--- Project ':core'
|    +--- Project ':core:base'
|    +--- Project ':core:extensions'
|    +--- Project ':core:network'
|    |    \--- Project ':core:network:ktor'
|    +--- Project ':core:sync'
|    +--- Project ':core:test'
|    \--- Project ':core:ui'
+--- Project ':data'
|    +--- Project ':data:api'
|    +--- Project ':local'
|    +--- Project ':remote'
|    \--- Project ':repository'
+--- Project ':domain'
|    +--- Project ':domain:auth'
|    +--- Project ':domain:market'
|    +--- Project ':domain:quiz-game'
|    +--- Project ':domain:question'
|    +--- Project ':domain:rank'
|    \--- Project ':domain:user'
+--- Project ':feature'
|    +--- Project ':feature:login'
|    +--- Project ':feature:rank'
|    +--- Project ':feature:stats'
|    \--- Project ':feature:quiz'
\--- Project ':library'
+--- Project ':library:designsystem'
\--- Project ':library:navigation'

This repo is a playground about best practices, using updated libraries and solutions in the Android world!

Check the apk [from here](asset/app_v1.0.0.apk)

## ⚙️ Architecture

![Architecture diagram](asset/architecture.jpg)

The main architecture of code based on MVI + CLEAN architecture. The division criteria is a hybrid strategy based on Feature + Layer by module.
For the detail of architecture, please read [this article](https://medium.com/@kaaveh/migrate-from-mvvm-to-mvi-f938c27c214f).

## 🚦 Navigation

For the detail of navigation implementations, please read [this article](https://proandroiddev.com/all-about-navigation-in-the-jetpack-compose-based-production-code-base-902706b8466d).

## 📱 Previewing

For the detail of handling preview of composable functions in this code-base, please read [this article](https://proandroiddev.com/an-introduction-about-preview-in-jetpack-compose-b72a96daac35).

## 🛠 Technologies

- Jetpack Compose
- CLEAN architecture
- MVI architectural pattern
- Coroutine Flow
- SQLDelight database
- Dagger Hilt
- Navigation
- Ktor client
- Work manager
- Unit test
- Support large screens
- Monochromatic app icon
- Version catalog & Convention Plugin (For the detail, please read [this article](https://proandroiddev.com/mastering-android-dependency-management-b94205595f6b))
- CI
- Git Hooks
- GitHub Actions
- Static Analysis(Kotlinter, Detekt) (For the detail, please read [this article](https://blog.kotlin-academy.com/detekt-gradle-configuration-guide-d6d2301b823a))

### We are porting the project to KMP. Here's the steps:
- [x] GSON &rarr; Kotlinx Serialization
- [x] ROOM &rarr; SQLDelight
- [x] Retrofit &rarr; Ktor
- [x] JUnit &rarr; Kotest
- [ ] Dagger-Hilt &rarr; Koin
- [ ] Jetpack Compose &rarr; Compose Multiplatform

## 📸 Screenshots

### Light theme

![](asset/light_mode.jpg)

### Dark theme

![](asset/dark_mode.jpg)

### Dynamic theme

![](asset/dynamic_color.jpeg)

### Large screen support (Foldable, Tablet, and Desktop)

![](asset/large_screen.jpg)

## Additional Resources

- [Git Hooks](documentation/GitHooks.md) - Learn about Git hooks used in this project for code formatting and analysis.
- [GitHub Actions](documentation/GitHubActions.md) - Explore the GitHub Actions workflows used to validate the code.
- [Static Analysis](documentation/StaticAnalysis.md) - Discover how static analysis tools like Detekt and Ktlint are used in this project for code quality assurance.
 