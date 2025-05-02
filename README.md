# 🛠️ Comandos Docker esenciales

Este proyecto utiliza **Docker Compose** para levantar todos los microservicios involucrados. A continuación se describen los comandos necesarios para ejecutar, detener, reconstruir y verificar el entorno completo.

### 1. Construir y ejecutar la aplicación con Docker Compose

```bash
# Construir las imágenes y levantar los contenedores
docker-compose up --build

# 🔄 Ejecutar en segundo plano
docker-compose up --build -d

# Detener y eliminar contenedores
docker-compose down -v