FROM openjdk:17
EXPOSE 8080
COPY build/libs/api-0.0.1-SNAPSHOT.jar app.jar
COPY agent.java/ agent.java/
COPY src/main/resources/application-deploy.properties application-db.properties
COPY startUp-deploy.sh startUp.sh
#ENTRYPOINT ["java"," -jar","/app.jar"]
#ENTRYPOINT ["java","-jar","/app.jar","-javaagent:agent.java/scouter.agent.jar","-Dscouter.config=agent.java/conf/scouter.conf","--add-opens=java.base/java.lang=ALL-UNNAMED","-Djdk.attach.allowAttachSelf=true","--spring.config.location=/application-db.properties"]
ENTRYPOINT ["./startUp.sh"]
