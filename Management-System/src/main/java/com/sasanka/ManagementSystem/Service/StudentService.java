package com.sasanka.ManagementSystem.Service;

import com.sasanka.ManagementSystem.Entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.sasanka.ManagementSystem.Repository.StudentRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
            return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        if (student.getAge() < 0){
            throw new IllegalStateException("Age cannot be negative");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException("Student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));
        if (name != null && name.length() > 0 && !name.equals(student.getName())){
            student.setName(name);
        }
    }
}
