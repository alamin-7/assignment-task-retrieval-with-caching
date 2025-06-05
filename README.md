# assignment-task-retrieval-with-caching

A Spring Boot application that retrieves task from a directory as `.json` file , with integrated caching and global exception handling.

## Features

- Retrieves Task by id(the file name) from local directory
- Global exception handling for:
  - Missing task files
  - Malformed JSON content
  - Unexpected server errors
 
- High-performance caching using Caffeine
- Unit testing using JUnit and MockMvc
 

## Tech Stack

- Java 17
- Spring Boot
- Spring Cache (Caffeine)
- JUnit 5
- Mockito
- ObjectMapper (Jackson)
- Gradle 

## Setup

### Prerequisites
- Java 17 installed

### Configuration
- Edit application.properties: Specify the local directory path where the tasks are located

### Steps to Run Locally

1. **Clone the repository**
   ```bash
   git clone https://github.com/alamin-7/assignment-task-retrieval-with-caching.git
   cd assignment-task-retrieval-with-caching

2. Build the project
   ```bash
   ./gradlew build

3. Run the project
   ```bash
   ./gradlew bootRun
4. Access the api
   i. The app will be running on: http://localhost:8080
   ii. Get task api: http://localhost:8080/getTask/{id_name}

### Test
```bash
./gradlew clean test

   

   

      
  

