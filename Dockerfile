FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY target/*.jar /temp/
CMD export JAR_FILENAME=$(ls /temp | grep .jar); \
    echo "---------------"; \
    echo $JAR_FILENAME; \
    echo "---------------"; \
    /bin/sh -c 'java -jar /temp/$JAR_FILENAME';