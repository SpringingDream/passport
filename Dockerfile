FROM maven:3-jdk-11
MAINTAINER Nikita Kiselev <docker@dekinci.com>

ENV WORK_DIR=/app
RUN mkdir -p $WORK_DIR
WORKDIR $WORK_DIR

ADD pom.xml $WORK_DIR
RUN ["mvn", "verify", "clean", "--fail-never"]

ADD . $WORK_DIR
RUN ["mvn", "package", "-Dmaven.test.skip=true", "-U"]

FROM openjdk:11
COPY --from=0 /app/target/marketplace-passport.jar /app/marketplace-passport.jar
CMD bash -c 'source ./entrypoint.sh && java -jar /app/marketplace-passport.jar'
EXPOSE 10000
