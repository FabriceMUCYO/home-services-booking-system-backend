# Home Services Booking System Backend

## Project Information
- **Project:** Home Services Booking System
- **Name:** MUCYO Fabrice
- **ID:** 27823

## Project Overview
A REST API built with Java Spring Boot and PostgreSQL to manage homeowners, technicians, service zones, job bookings, and work reports.

The system supports CRUD operations, booking validation, technician assignments, status management, statistics, dashboard summaries, pagination and sorting.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA/Hibernate
- PostgreSQL
- Maven
- Postman

## Requirements to Run the Project
Before running the application, install:
1. Java JDK compatible with the version configured in `pom.xml`
2. PostgreSQL Database
3. Maven 
4. Postman
5. VS Code or another Java IDE like Intellij

## How to Run Project
1. Clone the repository:
   ```bash
   git clone https://github.com/FabriceMUCYO/home-services-booking-system-backend.git
   ```
2. Open the project folder and create a PostgreSQL database
3. Configure the database URL, username, and password in `src/main/resources/application.properties`.
4. Start the application:
   ```bash
   mvn spring-boot:run
   ```
5. Test the API in Postman at `http://localhost:8083`

## Example of the Endpoints
- `GET http://localhost:8083/api/dashboard/summary` (Dashboard statistics)
- `GET http://localhost:8083/api/job-bookings/by-status?status=REQUESTED` (Filter bookings)
- `GET http://localhost:8083/api/service-zones/free` (Available service zones)

## API Testing
For The API Testing the Postman collection is provided as `home-services-booking-system-backend.postman_collection.json` in the repository root and contains requests named according to the assignment requirement.
