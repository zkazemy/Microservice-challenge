FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
ADD target/*.jar challenge.jar
ENV JAVA_OPTS "-Xmx192m -Xms192m -Djava.security.egd=file:///dev/./urandom -XX:+HeapDumpOnOutOfMemoryError "
EXPOSE 8585
CMD ["sh", "-c", "java ${JVM_OPTS} -jar challenge.jar"]