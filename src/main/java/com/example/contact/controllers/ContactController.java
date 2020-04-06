package com.example.contact.controllers;

import com.example.contact.entities.ContactEntity;
import com.example.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ContactController {
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public ResponseEntity getAllContacts(
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> q){
        Pageable pageable = PageRequest.of(offset.orElse(0), limit.orElse(10), Sort.Direction.ASC, sort.orElse("id"));
        Page<ContactEntity> contactEntityPage;
        if(q.isPresent()){
            contactEntityPage = contactService.findByName(q.get(), pageable);
        } else {
            contactEntityPage = contactService.findAllContact(pageable);
        }
        if(contactEntityPage.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(contactEntityPage, HttpStatus.OK);
        }
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity getContactById(@PathVariable("id") Integer id){
        Optional<ContactEntity> contactEntity = contactService.findById(id);
        if(contactEntity.isPresent()){
            return new ResponseEntity(contactEntity.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/contact")
    public ResponseEntity addNewContact(@RequestBody ContactEntity contactEntity){
        contactService.save(contactEntity);
        return new ResponseEntity(contactEntity, HttpStatus.CREATED);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity updateContact(@PathVariable("id") Integer id, @RequestBody ContactEntity contactEntity){
        Optional<ContactEntity> currentContactEntity = contactService.findById(id);
        if(!currentContactEntity.isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        currentContactEntity.get().setName(contactEntity.getName());
        currentContactEntity.get().setEmail(contactEntity.getEmail());
        currentContactEntity.get().setPhone(contactEntity.getPhone());
        contactService.save(currentContactEntity.get());
        return new ResponseEntity(currentContactEntity.get(), HttpStatus.OK);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity removeContact(@PathVariable("id") Integer id){
        Optional<ContactEntity> contactEntity = contactService.findById(id);
        if(!contactEntity.isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        contactService.remove(contactEntity.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    
}
