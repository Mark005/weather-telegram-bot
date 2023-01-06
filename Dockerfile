FROM openjdk:17
COPY target/*.jar /temp/weather-bot.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/temp/weather-bot.jar"]