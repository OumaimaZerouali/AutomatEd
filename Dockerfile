FROM maven:3.8-openjdk-17
WORKDIR /app
COPY pom.xml .
RUN mvn clean install
CMD ["java", "-jar", "target/AutomatEd.jar"]
