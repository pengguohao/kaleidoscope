FROM anapsix/alpine-java:8_server-jre_unlimited

RUN mkdir -p /kaleidoscope/log

WORKDIR /kaleidoscope/log

EXPOSE 8103

ADD ./target/kaleidoscope-log.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
