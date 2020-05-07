package com.example.contact.service.students;

import com.example.contact.entities.students.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {
    Page<StudentEntity> findAllStudents(Pageable pageable);

    Optional<StudentEntity> findById(Integer id);

    void save(StudentEntity studentEntity);

    void remove(StudentEntity studentEntity);

    Page<StudentEntity> findByName(String name, Pageable pageable);

}
