# Imagen base con Java 17
FROM openjdk:17

# Crear directorio dentro del contenedor
WORKDIR /app

# Copiar el JAR generado por Gradle usando comodín
COPY build/libs/*.jar /app/app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/app.jar"]