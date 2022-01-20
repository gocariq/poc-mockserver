FROM gradle:6.7.1-jdk11 AS build-env

# Compile the extensions (callbacks)
COPY /poc-mockserver /app
WORKDIR /app
RUN gradle build

# Compile the initializer for mock-server
RUN apt-get update
RUN apt-get -y install jq

COPY /config /config
RUN jq -s 'flatten' /config/mappings/*.json > /config/initializer.json

# MockServer
FROM mockserver/mockserver:latest

COPY --from=build-env /app/build/libs/poc-mockserver-*.jar /libs/
COPY --from=build-env /config/mockserver.properties /config/
COPY --from=build-env /config/initializer.json /config/

COPY /config/responses /config/responses

EXPOSE 1080

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-cp", "/mockserver-netty-jar-with-dependencies.jar:/libs/*", "-Dmockserver.initializationJsonPath=/config/initializer.json", "-Dmockserver.propertyFile=/config/mockserver.properties", "org.mockserver.cli.Main"]

CMD ["-serverPort", "1080"]