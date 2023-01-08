FROM openjdk:17
RUN echo PATH=$PATH;sudo ls $PATH;export JAR_FILENAME=$(/usr/bin/find ./target -type f -name "*.jar" -printf "%f\n");echo $JAR_FILENAME;
COPY target/$JAR_FILENAME /temp/$JAR_FILENAME
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/temp/$JAR_FILENAME"]
CMD ["echo", "$JAR_FILENAME"]