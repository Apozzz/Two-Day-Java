# Zoo Animals Management System

The Zoo Animals Management System is a Java application that allows you to manage animals and enclosures in a zoo. It provides functionality for adding, retrieving, and removing animals and enclosures, as well as assigning animals to enclosures based on their food requirements and available space.

## Author
Arnas Bosas - bosas.arnas2000@gmail.com

## Features

- Add new animals to the system
- Add new enclosures to the system
- Assign animals to enclosures based on their food requirements and available space
- Retrieve a list of animals and enclosures
- Remove animals and enclosures from the system
- Add/Remove animal From/To enclosure

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Web
- PostgreSQL
- Lombok
- Caffeine Cache

## Animals assignment logic
### How to assign
First it's a must to run POST /api/v1/enclosures command with enclosures JSON file.

After enclosures are saved, run POST /api/v1/animals command with animals JSON file. During this request, program assigns animals to existing enclosures and saves them.

### Logic behind assignment
1. Enclosures have different sizes, but none of them are defined except by words "Small", "Medium" and so on. 
    - "Small" enclosure is of 1 size unit
    - "Medium" enclosure is of 2 size units
    - "Large" enclosure is of 4 size units
    - "Huge" enclosure is of 8 size units
      
That means "Large" enclosure holds 2 times more animals than "Medium" and so on.

2. According to this logic, enclosure ratio is calculated (to know how many animals 1 size unit should hold)
So if there are in total of 100 animals and 4 "Medium" enclosures, then ratio is ceil of 100 / (4 * 2) = 12.5 = 13
That means that there are 13 spaces per unit, so "Medium" enclosure can hold 13 * 2 = 26 animals. That way all animals can be assigned
3. All enclosures are sorted by that calculated space Descending. All animals are sorted first by Food Type (Herbivores are on top) Descending and then by Amount Descending.
4. All animals are iterated and checked. Since lists are sorted, then highest amount herbivores are assigned to the most spacious enclosures, since many herbivore species can coexist.
5. If it is Carnivore and its Amount is equal to empty enclosures, then that Carnivore is assigned to enclosure.
6. If there are no more empty enclosures, then Carnivores are assigned together (at most 2 species).
7. If there is huge amount of one species, then that species are distributed to many available enclosures.
8. No animals of different Food types are assigned to one enclosure.


## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL database

### How to run
#### Running project code normally
1. Clone the repository:

```shell
git clone https://github.com/Apozzz/Two-Day-Java.git
```

2. Configure the database connection in the application.properties file:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/zoo
spring.datasource.username=db_user
spring.datasource.password=db_password
```

3. Build the project:
```
./mvnw clean package
```

4. Run the application:
```
./mvnw spring-boot:run
```

#### Running project code with containers

1. Copy docker-compose.yml file
2. Go to directory where docker-compose.yml is stationed and run command:
```
docker-compose up -d
``` 
3. This will download code image from Docker Repository and run it together with Postgres container

## Usage
### API Endpoints
The following API endpoints are available:

- GET /api/v1/animals - Retrieve a list of animals

- POST /api/v1/animals - Add new animals

- DELETE /api/v1/animals - Delete all animals

- GET /api/v1/enclosures - Retrieve a list of enclosures

- POST /api/v1/enclosures - Add new enclosures

- POST /api/v1/enclosures/{enclosureId}/animals/{animalId} - Add an animal to an enclosure

- DELETE /api/v1/enclosures - Delete all enclosures

- DELETE /api/v1/enclosures/{enclosureId}/animals/{animalId} - Remove an animal from an enclosure

### Example Requests
Add Animals

```
POST /api/v1/animals
Content-Type: application/json

{
    "animals": [
        {
            "species": "Lion",
            "food": "Carnivore",
            "amount": 2
        },
        {
            "species": "Tiger",
            "food": "Carnivore",
            "amount": 3
        },
        {
            "species": "Elephant",
            "food": "Herbivore",
            "amount": 5
        }
    ]
}
```

Add Enclosures
```
POST /api/v1/enclosures
Content-Type: application/json

{
    "enclosures": [
        {
            "name": "Enclosure 1",
            "size": "Large",
            "location": "Outside",
            "objects": [
                "Rocks",
                "Logs",
                "Water Pond"
            ]
        },
        {
            "name": "Enclosure 2",
            "size": "Medium",
            "location": "Outside",
            "objects": [
                "Climbing Structures",
                "Shelter",
                "Pool"
            ]
        },
        {
            "name": "Enclosure 3",
            "size": "Huge",
            "location": "Outside",
            "objects": [
                "Trees",
                "Mud Bath Area",
                "Water Trough"
            ]
        }
    ]
}
```
