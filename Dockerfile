FROM openjdk:17
ENV JAR_FILENAME=$(find ./target -type f -name "*.jar" -printf "%f\n")
COPY target/$JAR_FILENAME /temp/$JAR_FILENAME
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/temp/$JAR_FILENAME"]
CMD ["echo", "$JAR_FILENAME"]