#!/usr/bin/env bash

set -eo pipefail

function main() {
	cat <<EOF
===============================================================================
RemoteHabits-Android setup checklist

Follow the steps in to setup dev environment to be able to build and run the
RemoteHabits-Android app in the simulator or on a phone.
You can follow along and manually run all the steps, but some do offer you the
option to run the commands for you as well.
===============================================================================
EOF

	step 'Clone the "RemoteHabits-Android" repository by running:
	git clone git@github.com:customerio/RemoteHabits-Android.git' run_clone_repo

	step 'Download google credentials file and save it
- Open 1Password
- Search for "Remote Habits - google-services.json"
- Download the file and save in the "RemoteHabits-Android/app" directory'

	step 'Install Android Studio from their website or using homebrew by running:
	brew install --cask android-studio' run_install_android_studio

	step 'Configure project to add api credentials
- Open Android Studio
- Choose Open
- Navigate and select the RemoteHabits-Android directory
- Wait for Gradle sync to finish running
- Edit `local.properties` file and add:
	siteId=SITE-ID-GOES-HERE
	apiKey=API-KEY-GOES-HERE'

	step 'Optional - Create virtual device
- With Android Studio open 
- In the menu bar select Tools -> Device Manager
- In the Device Manager panel click on Create Device button
- Select phone hardware ex. Pixel 6
- Download a system image by clicking the download icon next to the release name ex. Tiramisu
- Follow prompts to get virtual device setup'

	step 'Build App
- With Android Studio open 
- In the menu bar select Run -> Run app'

}

# automated run functions to run the specified step

function run_clone_repo() {
	read -p "Directory where the repository should be cloned to: " dir
	if [[ ! -d "$dir" ]]; then
		echo "The directory $dir does not exist."
		exit 1
	fi

	repo_dir="$dir/RemoteHabits-Android"
	if [[ ! -d "$repo_dir" ]] ; then
	  git clone git@github.com:customerio/RemoteHabits-Android.git $repo_dir
	fi
}

function run_install_android_studio() {
	if [[ ! "$(command -v brew)" ]]; then
		echo "automated setup uses homebrew which is missing. install using:"
		echo '/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"'
		exit 1
	fi 

	brew install --cask android-studio
}

# helper functions

# helper utility to print the step and handle the user input.
# params:
# 	$1 - description of the step
# 	$2 - function to call if step can be run within the script if user opts for it
# 
function step() {
	((step_idx++))
	cat <<EOF

Step $step_idx: $1

EOF

	handle_input $2
}

# helper utility to ask for user input.
# params:
# 	$1 - if provided enables run option and calls function if selected
# 
function handle_input() {
	local prompt=''
	if [[ -n $1 ]]; then
		while true; do
			read -p "Choose [c]ontinue, [q]uit or [r]un: " answer
			case $answer in
				[Qq]* ) exit 0 ;;
				[Cc]* ) break ;;
				[Rr]* ) $1 && break ;;
			esac
		done
	else
		while true; do
			read -p "Choose [c]ontinue or [q]uit: " answer
			case $answer in
				[Qq]* ) exit 0 ;;
				[Cc]* ) break ;;
			esac
		done
	fi
}

main "$@"
