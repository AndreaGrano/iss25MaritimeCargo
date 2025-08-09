## -------------------------------------------------------
## Dockerfile for iss25MaritimeCargo.cargoservicegui_react
## -------------------------------------------------------

# Latest LTS version of node.js
FROM node:24.5.0-alpine

# Setting the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY ./package*.json ./

# Install dependencies
RUN npm install

# Copy of the remaining part of application files
COPY . .

# Expose the port the app runs on
EXPOSE 3000

## Launch of the application at the start
CMD ["npm", "start"]