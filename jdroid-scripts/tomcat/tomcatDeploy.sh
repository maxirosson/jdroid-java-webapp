#!/bin/bash

set -e

TOMCAT_HOME="$1"
WAR_NAME="$2"
PROJECT_NAME="$3"
PROJECT_HOME="$4"
BACKUPS=true
AUTO_RECOVERY=true
CLEAR_LOGS=true
TOMCAT_START_COMMAND=/etc/init.d/tomcat start
TOMCAR_STOP_COMMAND=/etc/init.d/tomcat stop

# Help
# ****
if [ $# -eq 1 ] && [ $1 = -h ]
then
        echo "Help"
        echo "****"
        echo ""
        echo "This script will deploy the application."
        echo "Available parameters"
        echo ""
        echo " 1) The Apache Tomcat path. Required. For example: /home/user/apache-tomcat-6.0.29"
        echo ""
        echo " 2) The name of the war file to deploy. Required."
        echo ""
        echo " 3) The project name. Required."
        echo ""
        echo " 4) The project home directory path. Required."
        echo ""
        exit 0
fi

# Parameters validation
# ************************
if [ -z "$TOMCAT_HOME" ]
then
	echo "[ERROR] The TOMCAT_HOME parameter is required"
	echo "Run the script with '-h' for help"
	exit 1;
fi

if [ ! -d "$TOMCAT_HOME" ]
then
	echo "[ERROR] - The TOMCAT_HOME directory does not exist."
	echo "Run the script with '-h' for help"
	exit 1;
fi

if [ -z "$WAR_NAME" ]
then
	echo "[ERROR] The WAR_NAME parameter is required"
        echo "Run the script with '-h' for help"
        exit 1
fi

WAR_PATH=$PROJECT_HOME/$WAR_NAME
filename=`echo $WAR_PATH | grep 'war$'`
if [ -z $filename ] || [ ! -e $WAR_PATH ]
then
        echo "[ERROR] The WAR_NAME parameter is not a war file"
        exit 0
fi

if [ -z "$PROJECT_NAME" ]
then
	echo "[ERROR] The PROJECT_NAME parameter is required"
	echo "Run the script with '-h' for help"
	exit 1;
fi

if [ -z "$PROJECT_HOME" ]
then
	echo "[ERROR] The PROJECT_HOME parameter is required"
	echo "Run the script with '-h' for help"
	exit 1;
fi

if [ ! -d "$PROJECT_HOME" ]
then
	echo "[ERROR] - The PROJECT_HOME directory does not exist."
	echo "Run the script with '-h' for help"
	exit 1;
fi

# Deployment
# *******************
echo "Starting deployment..."

if [ "$AUTO_RECOVERY" = 'true' ] 
then
	echo "Disabling autorecovery..."
	crontab -r
fi

echo "Shuting down tomcat..."
$TOMCAR_STOP_COMMAND

if [ "$CLEAR_LOGS" = 'true' ]
then
	echo "Clearing logs..."
	cd $TOMCAT_HOME/logs
	rm -rf *
fi

echo "Removing earlier versions..."
cd $TOMCAT_HOME/webapps
rm -rf $PROJECT_NAME*

echo "Deploying war..."
cp -p $WAR_PATH $TOMCAT_HOME/webapps/$PROJECT_NAME.war

echo "Starting tomcat..."
$TOMCAT_START_COMMAND

# Backups
# *******************
if [ "$BACKUPS" = 'true' ] 
then
    echo "Creating a backup copy..."
	timeStamp=`date +%F-%T`
	warName=`basename $WAR_PATH .war`
    backupFileName=$warName-[$timeStamp].war
    cp -p $WAR_PATH $PROJECT_HOME/backups/${backupFileName}
	rm $WAR_PATH
	echo "Backup created at $PROJECT_HOME/backups/${backupFileName}"
fi


# Auto recovery
# *******************
if [ "$AUTO_RECOVERY" = 'true' ] 
then
	echo "Enabling autorecovery..."
	echo "*/15 * * * * sudo sh $PROJECT_HOME/scripts/tomcatRecover.sh" | crontab
fi

echo "Deployment process finished successfully"
exit 0;
