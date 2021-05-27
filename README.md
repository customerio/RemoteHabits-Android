# Remote Habits

The Remote Habits Android app.

# Development

Here is how to get started with the project. Get it compiling on your machine.

1. Create a new file in the root level of your project, `secrets.properties`. Inside, put:

```
githubRepo.username="<github-username>"
githubRepo.token="<github-token>"
```

*Note: You will need to create a new GitHub personal access token that has access to private repos scope. Make sure your GitHub account has read access to the SDK's GitHub repo. Then, replace `<github-username>` with your GitHub username and `<github-token>` with the access token you just created.*

2. Build the app in Android Studio.

3. Setup git hooks to lint your code for you:

```
$ ./hooks/autohook.sh install
[Autohook] Scripts installed into .git/hooks
```

4. Install `ktlint` Kotlin linting CLI tool. The easiest way is `brew install ktlint` but if you are not on a Mac, [find another way to install](https://ktlint.github.io/#getting-started) on your machine. 

# Deployment

Here are instructions for CI setup. 

* Setup Firebase Test Lab so we can run Android instrumentation tests on it. We are using [this fastlane plugin](https://github.com/pink-room/fastlane-plugin-run_tests_firebase_testlab) to run tests in test lab.

* Create Service Accounts to authenticate CI server with Firebase. 

To create Service Accounts, go to Firebase project > Settings > Service accounts > Click on "X service accounts from Google Cloud Platform. It opens up Google Cloud Platform webpage for you. Your Firebase project should be selected as the Google Cloud project when this webpage is opened. This is because when you have a Firebase project, a Google Cloud project gets created for you without you even knowing it.

Create 2 service accounts:
1. `firebase test lab` with permissions `Project - Editor`
2. `firebase app distribution` with permissions `Firebase App Distribution Admin`

For both of these service accounts, create a private key file. Do this by clicking "Add key" > JSON under the service account you just created. Save this file to the tmp directory of your computer. 

* Run `cat /tmp/service-account-file-you-just-downloaded.json | base64` for both of the private keys that you downloaded. The strings printed to you are going to be used soon. Keep them handy. 

* While in the Google Cloud developer console, you need to go into the APIs section of the site and enable these 2 APIs: Cloud Testing API, Cloud Tool Results API. 

* Set the following secret environment variables:

1. `GH_REPO_USERNAME` - username of github user to authenticate with. This is just to read packages so we can pull the SDK successfully.
2. `GH_REPO_TOKEN` - personal github token with the scopes to read private repos and read packages. 
3. `FIREBASE_PROJECT_ID` - Firebase project for app. Find in firebase project settings > General tab. 
4. `REPO_PUSH_TOKEN` - personal github token to push to the repo. Make sure the github username for this token has push permissions for this repo. 
5. `FIREBASE_TEST_LAB_GOOGLE_AUTH` - base64 encoded string of the Firebase Test Lab Service Account private file you downloaded. 
6. `FIREBASE_APP_DISTRIBUTION_GOOGLE_AUTH` - base64 encoded string of the Firebase App Distribution Service Account private file you downloaded. 
7. `FIREBASE_APP_ID` - App ID for the app in your Firebase project. Find in firebase project settings > General tab > apps > select your app > app id. Format is similar to: `"1:1234567890:ios:0a1b2c3d4e5f67890"`
8. `RELEASE_KEYSTORE_BASE64` - base64 encoded string for the `keystores/upload.keystore` file used for app release signing. 
9. `ANDROID_SIGNING_STORE_PASSWORD` - store password for release signing configuration.
10. `ANDROID_SIGNING_ALIAS` - alias for the keystore file used for release signing. 
11. `ANDROID_SIGNING_KEY_PASSWORD` - key password for release signing configuration. 
