FROM openjdk:11

COPY ./build/*.jar  /parsley.jar
COPY ./src/main/docker/wait-for-it.sh /wait-for-it.sh
RUN ["chmod", "+x", "wait-for-it.sh"]

VOLUME maps

ENTRYPOINT ["./wait-for-it.sh", "-t", "10", "neo4j:7474", "--", "java", "-jar", "parsley.jar", "-f", "/maps"]