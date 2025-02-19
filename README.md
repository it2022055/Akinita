# ![akinita](https://github.com/user-attachments/assets/3813ada9-babe-47a9-8bad-642cc398c050) Akinita - Property Rental Management System

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Roles and Functions](#roles-and-functions)
   - [Administrator](#administrator)
   - [Owner](#owner)
   - [Renter](#renter)
4. [Getting Started](#getting-started)
   - [Prerequisites](#prerequisites)
   - [Installation](#installation)
     - [Clone the Repository](#clone-the-repository)
     - [Open the Project in IDE](#open-the-project-in-ide)
     - [Configure the Database](#configure-the-database)
     - [Run the Application](#run-the-application)
     - [Access the Web Application](#access-the-web-application)
5. [First-Time Execution](#first-time-execution)
6. [License](#license)

## Overview
Akinita is university project assignment. It implements a web-based platform designed to facilitate the management of property rentals. It allows property owners to list their properties, renters to apply for rentals, and an administrator to oversee and validate these transactions. The system is built as part of the Distributed Systems course for the academic year 2024-2025.

## Features
- **Property Listing Submission**: Property owners can submit listings that require approval from the administrator before being displayed on the platform.
- **Rental Applications**: Renters can apply for rental properties, and owners can review and confirm these applications.
- **Search and Filter**: Renters can search and filter available properties based on location and type.
- **Role-Based Dashboards**: Custom dashboards for administrators, owners, and renters to manage their respective tasks.

## Roles and Functions
### Administrator
- Approves new property listings.
- Verifies renters.
- Manages the platform.

### Owner
- Lists properties for rent.
- Reviews and approves rental applications.

### Renter
- Searches for rental properties.
- Submits rental applications.
- Can request property viewings.

## Getting Started
### Prerequisites
- Java Development Kit (JDK)
- Integrated Development Environment (IDE) like IntelliJ IDEA
- Web Browser (e.g., Chrome)
- PostgreSQL database (local or cloud-based, e.g., Render)

### Installation
#### Clone the Repository:
```bash
git clone https://github.com/it2022055/Akinita.git
```

#### Open the Project in IDE:
1. Launch IntelliJ IDEA.
2. Select **Open** and navigate to the cloned repository directory.
3. Open the project.


#### Configure the Database:
1. Set up a PostgreSQL database (locally or using a cloud service like Render).
2. Update the `application.properties` file with your database credentials:

```properties
spring.application.name=Akinita
server.port=8080

# Database Configuration
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
spring.datasource.url=jdbc:postgresql://YOUR_DATABASE_HOST:YOUR_DATABASE_PORT/YOUR_DATABASE_NAME
```

#### Run the Application:
1. Navigate to the main application file.
2. Run the application.

#### Access the Web Application:
Open a web browser and go to:
```
http://localhost:8080/
```

## First-Time Execution
When running the application for the first time, the following code should be commented in to ensure the creation of the initial admin user:

```java
User user = new User();
user.setUsername("admin");
String encodedPassword = passwordEncoder.encode("admin");
user.setPassword(encodedPassword);
user.setEmail("admin@example.com");
Set<Role> roles = new HashSet<>();
roles.add(role_admin);
user.setRoles(roles);
userRepository.save(user);
```

After the admin user is created, it is recommended to comment out this code to prevent re-execution.

## License
This project is for academic purposes and does not have a specific license.

