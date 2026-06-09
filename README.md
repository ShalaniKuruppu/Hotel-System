# Hotel-System

Monorepo for a hotel management system with an Angular front-end and a Spring Boot back-end.

## Contents

- `HotelClient/` — Angular client application
- `HotelServer/` — Spring Boot server application

## Overview

- The client is built with Angular (TypeScript) and contains UI, routing, and authentication components.
- The server is a Spring Boot (Java) application exposing REST endpoints and handling business logic.

## Prerequisites

- Node.js (16+ recommended) and npm
- Java 17+ (matching the project's JDK)
- Maven (optional — project includes Maven wrapper)
- Angular CLI (optional for development)

## Quick Start (development)

Run the server (Windows PowerShell):

```powershell
cd HotelServer
.\mvnw.cmd spring-boot:run
```

Run the client:

```powershell
cd HotelClient
npm install
npm start
# or: ng serve --open (if Angular CLI is installed)
```

Open the client typically at `http://localhost:4200` and the API at `http://localhost:8080`.

## Build (production)

Build server JAR:

```powershell
cd HotelServer
.\mvnw.cmd clean package
# then
java -jar target/*.jar
```

Build client:

```powershell
cd HotelClient
npm install
npm run build -- --prod
# Output will be in HotelClient/dist/
```

To serve the built client from the server, copy the client `dist` output into `HotelServer/src/main/resources/static` (or configure your preferred static hosting).

## Project Structure (high level)

- HotelClient/
  - src/
    - app/ — Angular modules, components, and services
    - assets/
  - angular.json, package.json

- HotelServer/
  - src/main/java/com/example/HotelServer — Java source
    - controller/ — REST controllers (e.g., auth)
    - service/ — business logic
    - repository/ — data access
  - src/main/resources/application.properties
  - pom.xml

## Testing

Run server tests:

```powershell
cd HotelServer
.\mvnw.cmd test
```

Run client tests:

```powershell
cd HotelClient
npm test
```

## Contributing

- Fork the repository and open a branch for your feature or fix.
- Keep client and server changes isolated where practical.
- Add tests for new behavior and ensure existing tests pass.


