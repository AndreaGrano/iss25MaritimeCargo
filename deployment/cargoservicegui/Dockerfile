## -------------------------------------------------
## Dockerfile for iss25MaritimeCargo.cargoservicegui
## -------------------------------------------------

## Official Images for OpenJDK binaries built by Eclipse Temurin
FROM eclipse-temurin:17.0.5_8-jre-focal

## ADD copies and extracts the distribution tar of the microservice
ADD ./build/distributions/iss25maritimecargo_cargoservicegui-1.0.tar /

# Expose the port used by the WebSocket
EXPOSE 8011

## Setting the working directory
WORKDIR /iss25maritimecargo_cargoservicegui-1.0/bin

## Launch of the application at the start
CMD ["bash", "iss25maritimecargo_cargoservicegui"]


## 1) gradle wrapper
## 2) gradlew disTar
## 3) docker build -t iss25maritimecargo.cargoservicegui:1.0 .