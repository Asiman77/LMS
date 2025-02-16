package com.example.library.repository;

import com.example.library.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByIdentifier(String identifier);
}



