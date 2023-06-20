# Spring boot API

Implementation of a management system for students in a Java Spring Boot application. The system includes entities such as **Student**, a repository (**StudentRepository**), a service (**StudentService**), and a controller (**StudentController**).


The Student entity represents a student with properties such as **ID, name, and date of birth**. The StudentRepository interface extends **JpaRepository** to provide basic CRUD operations for the Student entity. The StudentService class contains methods that interact with the repository to perform operations such as **retrieving students, adding new students, deleting students, and updating student information**. The StudentController class acts as a **RESTful API controller**, defining endpoints for handling HTTP requests related to students.


The **StudentConfig** class configures initial data for the **Student** entity by implementing the CommandLineRunner interface. It populates the repository with sample data using the saveAll method.

## Database

**spring.datasource.url:** Specifies the URL of the MySQL database, in this case, `jdbc:mysql://localhost:3306/springboot`. Change this URL to match the actual database you are using.

## Error Handelling

**server.error.include-message:** Controls whether error responses include detailed error messages. Setting it to always ensures that error messages are included in the response.

```Java
public void addNewStudent(Student student) {
        if (student.getAge() < 0){
            throw new IllegalStateException("Age cannot be negative");
        }
        studentRepository.save(student);
}
```

```JSON
{
  "timestamp": "2023-06-20T11:52:42.132+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Age cannot be negative",
  "path": "/api/v1/student"
}
```


## HTTTP Request Example:

### POST

```
POST /api/v1/student HTTP/1.1
Host: localhost:8080
Content-Type: application/json
```

```JSON
{
  "name": "Sasanka Weerakoon",
  "dob": "2000-08-13"
}
```

- Age will be Automatically handle using DOB.

### GET

```
GET /api/v1/student HTTP/1.1
Host: localhost:8080
```

### DELETE

```
DELETE http://localhost:8080/api/v1/student/2
```

### PUT

```
PUT http://localhost:8080/api/v1/student/2?name=Weerakoon
```


## LICENSE

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


