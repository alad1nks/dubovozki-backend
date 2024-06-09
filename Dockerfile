FROM openjdk:17-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
COPY certs/dubovozki.crt /app/certs/dubovozki.crt
COPY certs/dubovozki.key /app/certs/dubovozki.key
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-jar", "app.jar"]
