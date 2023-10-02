#!/bin/sh
java -javaagent:agent.java/scouter.agent.jar -Dscouter.config=agent.java/conf/scouter.conf --add-opens=java.base/java.lang=ALL-UNNAMED -Djdk.attach.allowAttachSelf=true -jar app.jar --spring.config.location=/application-db.properties
