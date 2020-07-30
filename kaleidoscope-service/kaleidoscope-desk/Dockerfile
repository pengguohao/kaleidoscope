FROM anapsix/alpine-java:8_server-jre_unlimited

RUN mkdir -p /kaleidoscope/desk

WORKDIR /kaleidoscope/desk

EXPOSE 8105

ADD ./target/kaleidoscope-desk.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
