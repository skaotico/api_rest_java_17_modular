# Imagen base con JDK completo
FROM openjdk:17-jdk

# Directorio de trabajo
WORKDIR /app

# Copiar JAR y wait-for-it.sh
COPY build/libs/*.jar /app/app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Exponer puerto
EXPOSE 8080

# Entrypoint usando wait-for-it.sh para esperar PostgreSQL
ENTRYPOINT ["/app/wait-for-it.sh", "postgres_db:5432", "--timeout=60", "--strict", "--", "java", "-jar", "/app/app.jar"]
