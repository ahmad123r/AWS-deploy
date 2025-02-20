package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student(
                    "Ahmad",
                    "Ahmad.Raddad@gmail.com",
                    LocalDate.of(2000, DECEMBER, 18));
            Student alex = new Student(
                    "George Wassouf",
                    "George Wassouf@gmail.com",
                    LocalDate.of(2004, JANUARY, 5));

            repository.saveAll(
                    List.of(mariam, alex));
        };
    }
}
