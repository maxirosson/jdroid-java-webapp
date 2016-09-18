#!/bin/sh

# Note the set -ev at the top. The -e flag causes the script to exit as soon as one command
# returns a non-zero exit code. The -v flag makes the shell print all lines in the script
# before executing them, which helps identify which steps failed.
set -e

BUILD_DIRECTORY=$1
BRANCH=$2

PROJECT_NAME=jdroid-java-webapp
TARGET_APP=staging

# ************************
# Parameters validation
# ************************

if [ -z "$BRANCH" ]
then
	BRANCH=master
fi

SOURCE_DIRECTORY=$BUILD_DIRECTORY/openshift/$PROJECT_NAME/$BRANCH
PROJECT_HOME=$SOURCE_DIRECTORY/$PROJECT_NAME
OPENSHIFT_HOME=$BUILD_DIRECTORY/openshift/$PROJECT_NAME/remote/$TARGET_APP/webapps

# ************************
# Checking out
# ************************

if [ ! -d "$SOURCE_DIRECTORY" ]
then
	# Clean the directories
	mkdir -p $SOURCE_DIRECTORY

	# Checkout the project
	cd $SOURCE_DIRECTORY
	echo Cloning git@github.com:maxirosson/$PROJECT_NAME.git
	git clone git@github.com:maxirosson/$PROJECT_NAME.git $PROJECT_NAME
fi

# ************************
# Synch $BRANCH branch
# ************************

cd $PROJECT_HOME
git add -A
git stash
git pull
git co $BRANCH
git pull

# ************************
# War generation
# ************************

./gradlew clean war
cp ./sample/build/libs/ROOT.war $OPENSHIFT_HOME

## Deploy

cd $OPENSHIFT_HOME
git add .
git commit -m 'Deploy'
git push
