FROM adoptopenjdk/openjdk11

ADD ../target/testTusk-1.0-SNAPSHOT.jar /home/testTusk-1.0-SNAPSHOT.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar  /home/testTusk-1.0-SNAPSHOT.jar" ]