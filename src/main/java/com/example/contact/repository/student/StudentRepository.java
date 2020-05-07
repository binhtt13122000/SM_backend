package com.example.contact.repository.student;

import com.example.contact.entities.students.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    Page<StudentEntity> findByNameContaining(String name, Pageable pageable);
}
