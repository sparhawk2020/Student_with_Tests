package com.example.student.repositories;

import com.example.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findStudentById (long kw);

    //List<Student> findStudentByIdContains (long kw);
}
