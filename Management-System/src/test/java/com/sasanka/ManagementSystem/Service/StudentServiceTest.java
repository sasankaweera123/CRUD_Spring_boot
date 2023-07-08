package com.sasanka.ManagementSystem.Service;

import com.sasanka.ManagementSystem.Entity.Student;
import com.sasanka.ManagementSystem.Enumeration.Gender;
import com.sasanka.ManagementSystem.Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    @Order(1)
    void canGetStudents() {
        underTest.getStudents(); // call the method
        verify(studentRepository).findAll(); // verify that the method was called
    }

    @Test
    @Order(2)
    void canGetStudentById() {
        Long id = 10L;
        Student mockStudent = new Student("Sasanka", LocalDate.of(1998, 1, 5), Gender.MALE);
        when(studentRepository.findById(id)).thenReturn(Optional.of(mockStudent)); // when the method is called, return the mock student
        Student student = underTest.getStudentById(id); // call the method
        assertThat(student).isEqualTo(mockStudent); // assert that the student is equal to the mock student
        verify(studentRepository).findById(id); // verify that the method was called

    }

    @Test
    @Order(3)
    void canAddNewStudent() {
        Student sasanka =new Student(
                "Sasanka",
                LocalDate.of(1998, 1, 5),
                Gender.MALE
        ); // create a new student
        underTest.addNewStudent(sasanka); // call the method

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class); // capture the student
        verify(studentRepository).save(studentArgumentCaptor.capture()); // verify that the method was called and capture the student

        Student capturedStudent = studentArgumentCaptor.getValue(); // get the captured student
        assertThat(capturedStudent).isEqualTo(sasanka); // assert that the captured student is equal to the student we created
    }

    @Test
    @Order(4)
    void willThrowWhenAgeIsNegative() {
        Student sasanka =new Student(
                "Sasanka",
                LocalDate.of(2030, 1, 5),
                Gender.MALE
        ); // create a new student

        assertThatThrownBy(() -> underTest.addNewStudent(sasanka))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Age cannot be negative");

        verify(studentRepository, never()).save(any()); // verify that the method was called and capture the student
    }

    @Test
    @Order(5)
    void canDeleteStudent() {
        Long id = 10L;
        when(studentRepository.existsById(id)).thenReturn(true); // when the method is called, return true
        underTest.deleteStudent(id); // call the method
        verify(studentRepository).deleteById(id); // verify that the method was called
    }

    @Test
    @Order(6)
    void ShouldThrowExceptionForNonExistingStudent() {
        Long id = 10L;
        when(studentRepository.existsById(id)).thenReturn(false); // when the method is called, return false
        assertThatThrownBy(() -> underTest.deleteStudent(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + id + " does not exist");
        verify(studentRepository, never()).deleteById(any()); // verify that the method was called
    }

    @Test
    @Order(7)
    void canUpdateStudent() {
        Long id = 10L;

        Student existingStudent = new Student(
                "Sasanka",
                LocalDate.of(2030, 1, 5),
                Gender.MALE
        );

        String updateName = "Sasanka Weerakoon";
        Gender updateGender = Gender.OTHER;

        Student updatedStudent = new Student(
                updateName,
                LocalDate.of(2030, 1, 5),
                updateGender
        );

        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent)); // when the method is called, return the existing student

        underTest.updateStudent(id, updatedStudent); // call the method

        verify(studentRepository).findById(id); // verify that the findById() method was called
        verify(studentRepository, never()).save(any(Student.class)); // verify that the save() method was not invoked

        assertThat(existingStudent.getName()).isEqualTo(updateName); // assert that the existing student's name is equal to the updated name
        assertThat(existingStudent.getGender()).isEqualTo(updateGender); // assert that the existing student's gender is equal to the updated gender
    }

    @Test
    @Order(8)
    void canNotUpdateStudent() {
        Long id = 10L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty()); // when the method is called, return an empty Optional

        String updateName = "Sasanka Weerakoon";
        Gender updateGender = Gender.OTHER;

        Student updatedStudent = new Student(
                updateName,
                LocalDate.of(2030, 1, 5),
                updateGender
        );

        assertThatThrownBy(() -> underTest.updateStudent(id, updatedStudent))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + id + " does not exist");

        verify(studentRepository).findById(id); // verify that the findById() method was called
        verify(studentRepository, never()).save(any(Student.class)); // verify that the save() method was not invoked
    }

}