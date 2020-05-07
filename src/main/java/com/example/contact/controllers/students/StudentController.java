package com.example.contact.controllers.students;

import com.example.contact.entities.students.StudentEntity;
import com.example.contact.service.students.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public ResponseEntity getAllStudents(
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> q) {
        Pageable pageable = PageRequest.of(offset.orElse(0), limit.orElse(10), Sort.Direction.ASC, sort.orElse("id"));
        Page<StudentEntity> studentEntityPage;
        if (q.isPresent()) {
            studentEntityPage = studentService.findByName(q.get(), pageable);
        } else {
            studentEntityPage = studentService.findAllStudents(pageable);
        }
        if (studentEntityPage.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(studentEntityPage, HttpStatus.OK);
        }
    }

    @GetMapping("/students/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity getStudentById(@PathVariable("id") Integer id) {
        Optional<StudentEntity> studentEntity = studentService.findById(id);
        if (studentEntity.isPresent()) {
            return new ResponseEntity(studentEntity.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/student")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity addNewStudent(@RequestBody StudentEntity studentEntity) {
        studentService.save(studentEntity);
        return new ResponseEntity(studentEntity, HttpStatus.CREATED);
    }

    @PutMapping("/student/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity updateStudent(@PathVariable("id") Integer id, @RequestBody StudentEntity studentEntity) {
        Optional<StudentEntity> currentStudentEntity = studentService.findById(id);
        if (!currentStudentEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        currentStudentEntity.get().setName(studentEntity.getName());
        currentStudentEntity.get().setEmail(studentEntity.getEmail());
        currentStudentEntity.get().setPhone(studentEntity.getPhone());
        studentService.save(currentStudentEntity.get());
        return new ResponseEntity(currentStudentEntity.get(), HttpStatus.OK);
    }

    @DeleteMapping("/student/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity removeStudent(@PathVariable("id") Integer id) {
        Optional<StudentEntity> studentEntity = studentService.findById(id);
        if (!studentEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        studentService.remove(studentEntity.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
