package com.example.contact.service.students;

import com.example.contact.entities.students.StudentEntity;
import com.example.contact.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyStudentService implements StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public MyStudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<StudentEntity> findAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<StudentEntity> findById(Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    public void save(StudentEntity studentEntity) {
        studentRepository.save(studentEntity);
    }

    @Override
    public void remove(StudentEntity studentEntity) {
        studentRepository.delete(studentEntity);
    }

    @Override
    public Page<StudentEntity> findByName(String name, Pageable pageable) {
        return studentRepository.findByNameContaining(name, pageable);
    }


}
