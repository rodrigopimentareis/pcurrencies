# Currency Converter

## Introduction

This document outlines the steps to implement a full-stack currency converter application. The application will use a
Java-based backend (Quarkus) for API services and a React.js (with TypeScript) frontend for the user interface. The
application will allow users to convert between different currencies using real-time exchange rates from the Swop.cx
API.

The goal of this assignment is to evaluate your ability to build a full-stack application while adhering to modern
development practices such as validation, caching, testing, and proper security measures.

## Architecture Overview

The system will be built using a microservice architecture that separates the frontend (React.js with TypeScript)
and backend (Quarkus). The backend will provide a RESTful API that communicates with the Swop.cx API to fetch exchange
rates and handle currency conversion. The frontend will display the user interface and allow users to interact with the
backend.

### Backend (Quarkus)

- The backend is built with Quarkus, a Java framework known for its fast startup times and low memory footprint.
- The backend will expose a RESTful API endpoint to perform currency conversion.
- The backend will use the Swop.cx API to fetch real-time exchange rates and cache its request. Cache is automatically
  invalidated every hour
- It will include validation to ensure that only supported currencies are used.

### Frontend (React.js with TypeScript)

- The frontend will be a React application using TypeScript to ensure type safety and scalability.
- The user will be able to select a source and target currency, enter an amount, and see the converted result.
- The frontend will call the backend API to request conversion data and display the results.
- The frontend will handle error messages if the input is invalid or if the backend fails.
- Vite was use for quick setup

## Testing strategy

The approach prioritizes component and integration tests, with minimal unit tests for pure functions and edge
cases. The focus is on real-world behavior with limited use of mocks, in order to make tests more realistic and
more maintainable.

### Key Principles

- **Component/Integration Tests**
    - **Backend**: Validate service interactions, API behavior, and external dependencies.
    - **Frontend**: Test React components with actual data flow and API calls.

- **Unit Tests**
    - Cover only pure functions and critical edge cases (e.g., invalid inputs, error handling).

- **Minimal Mocks**
    - Used only for external dependencies when necessary or hard to replicate scenarios.

- **Test Coverage**
    - Focus on core functionality, user flows, and error scenarios.

Cypress was chosen for its robust end-to-end testing capabilities and built-in support for UI interaction testing, but
other tools like Playwright or Testing Library could also be used depending on project needs.

## Setup Instructions

### Backend

1. **Install Java 21**:

- Ensure that you have Java 21 installed on your machine.

2. **Compile**:
   ```bash
   cd backend
   ./mvnw clean package

Add the swop key to application.properties

2. **Test automatically**:
   ```bash
   quarkus test
3. **Check swagger and dev service in your browser**:
   http://localhost:8080/q/swagger-ui
   http://localhost:8080/q/dev-ui
4. **Run**:
   ```bash
   quarkus dev

### Frontend

1. **Install node > v22.13.0**:

- Ensure that you have node 22 installed on your machine.

2. **Install dependencies**:
   ```bash
   cd frontend
   npm i

2. **Test automatically**:
   ```bash
   npx cypress run --component
   npm run test

2. **Run**:
    ```bash
   npm run dev 

2. **Develop tests with cypress**:
    ```bash
   npx cypress open

# TODO and improvements

* Add docker and helm files
* Inject secrets during deployment
* Improve test coverage in both components.
* Make frontend configurable with .env
* Add linters and automatic formatting for both frontend/backend
* Add backup service for Swop in backend
* Implement authentication/authorization
* Now the pairs of currencies are not validated. The backend needs to return the list of valid pair of a currency so the
  frontend can add them to the selectors dynamically.
* Improve error handling in frontend. The way it is now, they are not translated, and it might show some implementation
  details to the user.
* Add one e2e test with cypress.
* Improve testing code reusability