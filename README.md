# BBit Using Compose Multiplatform
This is a sample application using Compose Multiplatform that works on Android and iOS platforms. The app uses [the Bitbucket APIs](https://developer.atlassian.com/cloud/bitbucket/rest/intro/#authentication) for [authentication](https://developer.atlassian.com/cloud/bitbucket/oauth-2/) and fetching all information of a particular user.

In this branch you will find the following project structure and libraries used:
- Model-View-ViewModel with Clean Arichitecture (Optional)
- User Interface built with [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/) and [Material3]
- A single-activity architecture using [Navigation Compose](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html).
- Shared `ViewModel` between platforms [Common ViewModel](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html), this is still in experimental.
- Dependency injection using [Koin](https://insert-koin.io/docs/reference/koin-compose/compose/) to inject dependencies including `Compose ViewModel`
- Project build configuration using [BuildKonfig](https://github.com/yshrsmz/BuildKonfig?tab=readme-ov-file#buildkonfig)
- Asynchronous Http connection using [Ktor](https://ktor.io/docs/client-create-new-application.html) with [Kotlin Serialization](https://ktor.io/docs/client-create-new-application.html)
- Local data storage using [Preference Datastore](https://developer.android.com/kotlin/multiplatform/datastore)

## Preriquisites
Before you start, please visit these [instruction](https://github.com/JetBrains/compose-multiplatform/tree/master/examples/codeviewer) to setup the environment.
Also, you can use the [KDoctor](https://github.com/Kotlin/kdoctor) tool to ensure that your development environment
is configured correctly:

1. Install KDoctor with [Homebrew](https://brew.sh/):

    ```text
    brew install kdoctor
    ```

2. Run KDoctor in your terminal:

    ```text
    kdoctor
    ```

   If everything is set up correctly, you'll see the following output in your terminal:

   ```text
   Environment diagnose (to see all details, use -v option):
   [✓] Operation System
   [✓] Java
   [✓] Android Studio
   [✓] Xcode
   [✓] Cocoapods
   
   Conclusion:
     ✓ Your system is ready for Kotlin Multiplatform Mobile development!
   ```

Otherwise, KDoctor will result in a failed output with problem descriptions and possible solutions.

## Environment Variables
Since this application uses the Bitbucket API for authentication and fetching information, you will need to create a client ID and a client key, then add them in a `gradle.properties` file (system, proejct, or Gradle) or an environment variables to keep out of a verion control and to support CI/CD. You can visit [here](https://support.atlassian.com/bitbucket-cloud/docs/use-oauth-on-bitbucket-cloud/) to generate a new client.

`gradle.properties`
```text
BITBUCKET_CLIENT_ID=<Your client id>
BITBUCKET_CLIENT_KEY=<Your client key>
BITBUCKET_AUTH_CALLBACK_URL=<Callback url based on cliet id>
```

## Screenshots
- ### Android
- ### iOS
