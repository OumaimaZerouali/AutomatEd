FROM maven:3.8-openjdk-17
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install
CMD ["java", "-jar", "target/AutomatEd.jar"]
