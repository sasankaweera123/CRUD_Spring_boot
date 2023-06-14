package com.sasanka.ManagementSystem.Repository;

import com.sasanka.ManagementSystem.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
