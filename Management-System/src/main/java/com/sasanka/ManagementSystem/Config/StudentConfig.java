package com.sasanka.ManagementSystem.Config;

import com.sasanka.ManagementSystem.Entity.Student;
import com.sasanka.ManagementSystem.Repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student sasanka =new Student(
                    "Sasanka",
                    LocalDate.of(1998, 1, 5)
            );

            Student weera = new Student(
                    "Weera",
                    LocalDate.of(2000, 1, 5)
            );

            repository.saveAll(
                    List.of(sasanka, weera)
            );

        };
    }
}
