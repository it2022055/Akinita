## ![akinita](https://github.com/user-attachments/assets/172f5c28-119b-4cc1-be43-34318618c07b) Akinita 
Property Rental Management System

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

## Overview
Akinita is university project assignment. It implements a web-based platform designed to facilitate the management of property rentals. It allows property owners to list their properties, renters to apply for rentals, and an administrator to oversee and validate these transactions. The system is built as part of the Distributed Systems course for the academic year 2024-2025.

Images 
   Homepage
   
   ![image](https://github.com/user-attachments/assets/f1e4bd81-cc79-4fc0-bad9-10a449185879)
   
   Admin view
   
   ![image](https://github.com/user-attachments/assets/683dd013-ca6b-4d9d-8d1f-98f5b2cebbc8)

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
- Java Development Kit (JDK) (Java 22) 
- Integrated Development Environment (IDE) like IntelliJ IDEA
- Web Browser (e.g., Chrome)
- PostgreSQL database (local or cloud-based, e.g., Render)

### Installation
#### Clone the Repository:
```bash
git clone https://github.com/it2022055/Akinita.git
```

####  Navigate to the Project Directory
Open a terminal and move into your project folder:
```sh
cd /path/to/your/project
```

#### Build the project:
```sh
mvn clean install
```


#### Configure the Database:
1. Set up a PostgreSQL database (locally or using a cloud service like Render).
2. Update the `application.properties` file with your database credentials:

```properties
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
spring.datasource.url=jdbc:postgresql://YOUR_DATABASE_HOST:YOUR_DATABASE_PORT/YOUR_DATABASE_NAME
```

#### Run the Application:
```sh
java -jar target/your-app-name.jar
```

#### Access the Web Application:
Open a web browser and go to:
```
http://localhost:8080/
```
