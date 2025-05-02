FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ADD /target/elo-baby-0.0.1-SNAPSHOT.jar elobaby_spring.jar
RUN mkdir "images"
ENTRYPOINT ["java","-jar","/elobaby_spring.jar","--spring.profiles.active=${PROFILE}"]