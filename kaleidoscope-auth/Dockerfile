FROM anapsix/alpine-java:8_server-jre_unlimited

RUN mkdir -p /kaleidoscope/auth

WORKDIR /kaleidoscope/auth

EXPOSE 8010

ADD ./target/kaleidoscope-auth.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
