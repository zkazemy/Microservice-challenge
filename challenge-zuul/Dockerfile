FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
ADD target/*.jar challenge-zuul.jar
ENV JAVA_OPTS "-Xmx192m -Xms192m -Djava.security.egd=file:///dev/./urandom -XX:+HeapDumpOnOutOfMemoryError "
EXPOSE 8181
CMD ["sh", "-c", "java ${JVM_OPTS} -jar challenge-zuul.jar"]