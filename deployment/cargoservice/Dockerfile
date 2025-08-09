## ----------------------------------------------
## Dockerfile for iss25MaritimeCargo.cargoservice
## ----------------------------------------------

## Official Images for OpenJDK binaries built by Eclipse Temurin
FROM eclipse-temurin:17.0.5_8-jre-focal

## ADD copies and extracts the distribution tar of the microservice
ADD ./build/distributions/iss25maritimecargo_cargoservice-1.0.tar /

## Setting the working directory
WORKDIR /iss25maritimecargo_cargoservice-1.0/bin

# Copy hold's map files
COPY ./*.bin ./
COPY ./*.txt ./

## Launch of the application at the start
CMD ["bash", "iss25maritimecargo_cargoservice"]


## 1) gradle wrapper
## 2) gradlew disTar
## 3) docker build -t iss25maritimecargo.cargoservice:1.0 .