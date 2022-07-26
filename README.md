# satellite-communicator

Plataforma de la aplicación:
- Java 1.8.0_231,
- Maven 3.6.3,
- Mysql 8.0.23 (con un esquema vacío llamado "satellite_messenger"),
- Ide utilizado: IntelliJ Idea Community Edition,
- Cloud SDK version: 337.0.0,

Cómo ejecutar la aplicacion:
- Generar el compilado de la aplicación con "mvn clean install",
- Correr la aplicación con "./mvnw spring-boot:run"
- Nota: Es necesario insertar en la tabla "satellite" al menos tres registros para que funcione el servicio de decodificación asíncrona,
