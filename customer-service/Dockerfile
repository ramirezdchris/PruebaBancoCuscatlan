# Etapa de construcción
FROM eclipse-temurin:21-jdk as build

WORKDIR /workspace/app

# Copia solo lo necesario para el primer paso (optimiza caché)
COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline -B

# Luego copia el resto del código
COPY src src

# Construye el JAR
RUN ./mvnw clean package -DskipTests -B

# Etapa de ejecución
FROM eclipse-temurin:21-jre

# Directorio de trabajo
WORKDIR /app

# Copia el JAR desde el contenedor anterior
COPY --from=build /workspace/app/target/*.jar app.jar

# Exponer puerto (opcional si lo deseas)
EXPOSE 8080

# Instrucción de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
