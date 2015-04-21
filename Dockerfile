FROM ichigotake/docker-android-base:latest

MAINTAINER ichigotake <ichigotake.san@gmail.com>

ENV JAVA_HOME $JAVA8_HOME
ENV PROJECT /project
WORKDIR $PROJECT

ADD . $PROJECT

RUN echo "sdk.dir=$ANDROID_HOME" > local.properties
RUN ./gradlew --stacktrace androidDependencies

CMD ./gradlew --stacktrace test build

