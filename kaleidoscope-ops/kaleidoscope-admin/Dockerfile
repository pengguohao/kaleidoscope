FROM anapsix/alpine-java:8_server-jre_unlimited

RUN mkdir -p /kaleidoscope/admin

WORKDIR /kaleidoscope/admin

EXPOSE 7002

ADD ./target/kaleidoscope-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
