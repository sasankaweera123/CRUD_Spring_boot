package com.sasanka.ManagementSystem.Controller;

import com.sasanka.ManagementSystem.Entity.Student;
import com.sasanka.ManagementSystem.Enumeration.Gender;
import com.sasanka.ManagementSystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public Student getStudentById(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    public ResponseEntity<String> addNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
        return ResponseEntity.ok("Student added successfully as id :"+ student.getId());
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok("Student with id " + studentId + " deleted successfully");
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Gender gender) {
        studentService.updateStudent(studentId, name, gender);
    }
}

