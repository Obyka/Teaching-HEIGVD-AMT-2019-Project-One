#!/bin/sh
#sudo keytool -delete -alias payara -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts ## Remove the certificate
sudo keytool -trustcacerts -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit -alias payara -import -file ./payara-self-signed-certificate.crt