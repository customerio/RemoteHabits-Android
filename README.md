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

# Deployment

On the CI server, set these secret environment variables

* `GITHUB_REPO_USERNAME` - username of github user to authenticate with. This is just to read packages so we can pull the SDK successfully.
* `GITHUB_REPO_TOKEN` - personal github token with the scopes to read private repos and read packages. 