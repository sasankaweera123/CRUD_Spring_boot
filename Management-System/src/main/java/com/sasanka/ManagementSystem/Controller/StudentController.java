package com.sasanka.ManagementSystem.Controller;

import com.sasanka.ManagementSystem.Entity.Student;
import com.sasanka.ManagementSystem.Enumeration.Gender;
import com.sasanka.ManagementSystem.Model.Response;
import com.sasanka.ManagementSystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "list")
    public ResponseEntity<Response> getStudents() {
        return ResponseEntity.ok(
                Response.builder().timeStamp(LocalDateTime.now())
                        .data(Map.of("students", studentService.getStudents()))
                        .message("Students fetched successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "{studentId}")
    public ResponseEntity<Response> getStudentById(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(
                Response.builder().timeStamp(LocalDateTime.now())
                        .data(Map.of("student", studentService.getStudentById(studentId)))
                        .message("Student fetched successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Response> addNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
        return ResponseEntity.ok(
                Response.builder().timeStamp(LocalDateTime.now())
                        .data(Map.of("student", studentService.getStudentById(student.getId())))
                        .message("Student added successfully as id :" + student.getId())
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<Response> deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok(
                Response.builder().timeStamp(LocalDateTime.now())
                        .message("Student with id " + studentId + " deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<Response> updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Gender gender) {
        studentService.updateStudent(studentId, name, gender);
        return ResponseEntity.ok(
                Response.builder().timeStamp(LocalDateTime.now())
                        .data(Map.of("student", studentService.getStudentById(studentId)))
                        .message("Student with id " + studentId + " updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}

