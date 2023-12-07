#!/bin/sh
java -Xms512m -Xmx512m -javaagent:agent.java/scouter.agent.jar -Dscouter.config=agent.java/conf/scouter.conf --add-opens=java.base/java.lang=ALL-UNNAMED -Djdk.attach.allowAttachSelf=true -agentlib:jdwp=transport=dt_socket,server=n,address=host.docker.internal:5005,suspend=y -jar app.jar --spring.config.location=/application-db.properties
