#!/bin/bash

apt-get update
apt-get install -y openjdk-7-jdk
export JAVA_HOME="/usr/lib/jvm/java-7-openjdk-amd64"
apt-get install -y git
apt-get install -y maven
apt-get install -y rabbitmq-server
apt-get install -y tomcat7
apt-get install -y mongodb
git clone https://github.com/veyronfei/clouck.git
cd clouck/
mvn clean package
cp target/clouck.war /var/lib/tomcat7/webapps/