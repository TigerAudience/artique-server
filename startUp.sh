#!/bin/sh
/Users/kimtaehyung/Library/Java/JavaVirtualMachines/corretto-17.0.4/Contents/Home/bin/java -Xms512m -Xmx512m -javaagent:/Users/kimtaehyung/Documents/scouter/agent.java/scouter.agent.jar -Dscouter.config=/Users/kimtaehyung/Documents/scouter/agent.java/conf/scouter.conf --add-opens=java.base/java.lang=ALL-UNNAMED -Djdk.attach.allowAttachSelf=true -jar ./build/libs/api-0.0.1-SNAPSHOT.jar
