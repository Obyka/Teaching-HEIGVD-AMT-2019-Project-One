#!/bin/sh
 #sudo keytool -delete -alias payara -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts ## Remove the certificate
sudo keytool -trustcacerts -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit -alias payara -import -file /home/jordan/Documents/AMT/Labos/Project-One/Teaching-HEIGVD-AMT-2019-Project-One/AMT-Project-One/ssl/payara-self-signed-certificate.crt
keystore