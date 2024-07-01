FROM openjdk:17-alpine
WORKDIR /opt/app
COPY build/libs/sumkin-telegram-bot-0.0.1-SNAPSHOT.jar frontendbot.jar
ENTRYPOINT ["java","-jar","frontendbot.jar"]