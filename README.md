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

1. You need to create a Service Account on Google Cloud to authenticate into your Google Cloud project. To do this, Firebase project > Settings > Service accounts > Click on "X service accounts from Google Cloud Platform. It opens up Google Cloud Platform webpage for you. Your Firebase project should be selected as the Google Cloud project when this webpage is opened. This is because when you have a Firebase project, a Google Cloud project gets created for you without you even knowing it.
2. If you have not already created a service account *just for firebase test lab* then let's create one. Click "Create service account" > for the name enter "firebase test lab" > for roles/permissions select "Project - Editor" > Create.
3. Once you created the service account you need to then create a key file for this account to authenticate with this service account. Do this by clicking "Add key" > JSON under the service account you just created. Save this file to the tmp directory of your computer. 
4. Run `cat /tmp/service-account-file-you-just-downloaded.json | base64`. The string printed to you is going to be used soon. Keep it handy. 
5. While in the Google Cloud developer console, you need to go into the APIs section of the site and enable these 2 APIs: Cloud Testing API, Cloud Tool Results API. 

* Set the following secret environment variables:

1. `GH_REPO_USERNAME` - username of github user to authenticate with. This is just to read packages so we can pull the SDK successfully.
2. `GH_REPO_TOKEN` - personal github token with the scopes to read private repos and read packages. 
3. `FIREBASE_PROJECT_ID` - Firebase project for app. Find in firebase project settings > General tab. 
4. `REPO_PUSH_TOKEN` - personal github token to push to the repo. Make sure the github username for this token has push permissions for this repo. 
5. `FIREBASE_TEST_LAB_GOOGLE_AUTH` - base64 encoded string of the Firebase Test Lab Service Account to authenticate the CI server with Firebase. 
