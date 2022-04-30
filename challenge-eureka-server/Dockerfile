FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
ADD target/*.jar challenge-eureka.jar
ENV JAVA_OPTS "-Xmx192m -Xms192m -Djava.security.egd=file:///dev/./urandom -XX:+HeapDumpOnOutOfMemoryError "
EXPOSE 8761
CMD ["sh", "-c", "java ${JVM_OPTS} -jar challenge-eureka.jar"]