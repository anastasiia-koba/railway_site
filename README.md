Build and deploy

First, it is important to create database scheme railway_site_db via command in MySql-console : CREATE DATABASE railway_site_db.
Second, run script database.sql in resources folder. It create all necessary tables and links between them.
Third, load and install WildFly server.
Fourth, in main folder of project run command: mvn clean install wildfly:wildly.
After this, in the folder where wildfly is stored via command cd /standalone/configuration gets into configuration folder, where it is necessary rename standalone-full.xml to standalone.xml, then edit this fail. In description of jms-queue it is necessary add <jms-topic name="rwTopic" entries="java:/jms/topic/rw"/>, and in deployments-section: 
<deployment name="mysql-connector-java-8.0.11.jar" runtime-name="mysql-connector-java-8.0.11.jar">
            <content sha1="2c3d25fe1dfdd6496e0bbe47d67809f67487cfba"/>
        </deployment>
Then via command in console: cd ../../bin gets into run-folder and run standalone.sh
