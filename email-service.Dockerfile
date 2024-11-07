FROM maven:3.9.9-eclipse-temurin-21-alpine AS base

WORKDIR /dep

COPY CommonService/pom.xml .
COPY CommonService/src ./src
RUN mvn install -DSkipTest


FROM base AS build

WORKDIR /app

COPY EmailService/pom.xml .
COPY EmailService/src ./src

COPY --from=base /dep/target/CommonService-1.0.0.jar .
RUN mvn install -DSkipTest


FROM build AS prod

WORKDIR /application

COPY --from=build /app/target/EmailService-1.0.0.jar output/app.jar
COPY --from=build /app/src/main/resources/application.properties config/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/application/output/app.jar"]