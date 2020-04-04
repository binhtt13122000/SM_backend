package com.example.contact.controllers;

import com.example.contact.entities.ContactEntity;
import com.example.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy){
        Page<ContactEntity> contactEntityList =
                contactService.findAllContact(PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, sortBy.orElse("id")));
        if(contactEntityList.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(contactEntityList, HttpStatus.OK);
        }
    }
    @GetMapping("/contact")
    public ResponseEntity getContactsByName(@RequestParam Optional<String> name){
        List<ContactEntity> contactEntities = contactService.findByName(name.orElse(""));
        if(contactEntities.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(contactEntities, HttpStatus.OK);
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
