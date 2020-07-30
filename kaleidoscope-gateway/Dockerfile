FROM anapsix/alpine-java:8_server-jre_unlimited

RUN mkdir -p /kaleidoscope/gateway

WORKDIR /kaleidoscope/gateway

EXPOSE 80

ADD ./target/blade-gateway.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
