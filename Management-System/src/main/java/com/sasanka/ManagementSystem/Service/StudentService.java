package com.sasanka.ManagementSystem.Service;

import com.sasanka.ManagementSystem.Entity.Student;
import com.sasanka.ManagementSystem.Enumeration.Gender;
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
    public void updateStudent(Long studentId, Student student) {
        Student studentFromId = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));
        if (student.getName() != null && student.getName().length() > 0 && !student.getName().equals(studentFromId.getName())){
            studentFromId.setName(student.getName());
        }
        if (student.getDob() != null && !student.getDob().equals(studentFromId.getDob())){
            studentFromId.setDob(student.getDob());
        }
        if (student.getGender() != null && !student.getGender().equals(studentFromId.getGender())){
            studentFromId.setGender(student.getGender());
        }
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));
    }
}
