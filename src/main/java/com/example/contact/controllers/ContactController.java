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
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ContactController {
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public ResponseEntity getAllContacts(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,@RequestParam Optional<String> sortBy){
        Page<ContactEntity> contactEntityList =
                contactService.findAllContact(PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, sortBy.orElse("id")));
        if(contactEntityList.isEmpty()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(contactEntityList, HttpStatus.OK);
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
//    @GetMapping("/contacts")
//    public ResponseEntity getContactByPage(@RequestParam Optional<String> name, @RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy){
//        Page<ContactEntity> contactEntityPage = contactService.findByName(name.orElse("_"), PageRequest.of(page.orElse(0),
//                5, Sort.Direction.ASC, sortBy.orElse("id")));
//        if(contactEntityPage.isEmpty()){
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        } else {
//            return new ResponseEntity(contactEntityPage, HttpStatus.OK);
//        }
//    }

    @PostMapping("/contact")
    public ResponseEntity addNewContact(@RequestBody Map<String, String> body){
        String name = body.get("name");
        String email = body.get("email");
        String phone = body.get("phone");
        ContactEntity contactEntity = paramAccountEntityCreateRequest(name, email, phone);
        contactService.save(contactEntity);
        return new ResponseEntity(contactEntity, HttpStatus.CREATED);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity updateContact(@PathVariable("id") Integer id, @RequestBody Map<String, String> body){
        Optional<ContactEntity> contactEntity = contactService.findById(id);
        if(!contactEntity.isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        String name = body.get("name");
        String email = body.get("email");
        String phone = body.get("phone");
        ContactEntity contactEntityCurrent = contactEntity.get();
        contactEntityCurrent.setName(name);
        contactEntityCurrent.setEmail(email);
        contactEntityCurrent.setPhone(phone);
        contactService.save(contactEntityCurrent);
        return new ResponseEntity(contactEntityCurrent, HttpStatus.OK);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity removeContact(@PathVariable("id") Integer id){
        Optional<ContactEntity> contactEntity = contactService.findById(id);
        if(!contactEntity.isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
//        Integer idOfRemoveItem = contactEntity.get().getId();
        contactService.remove(contactEntity.get());
//        List<ContactEntity> contactEntityList = contactService.findAllContact();
//        Iterator<ContactEntity> iterator = contactEntityList.iterator();
//        while (iterator.hasNext()){
//            ContactEntity contactEntityCurrent = iterator.next();
//            if(contactEntityCurrent.getId() > idOfRemoveItem.intValue()){
//                contactEntityCurrent.setId(contactEntityCurrent.getId() + 1);
//                contactService.save(contactEntityCurrent);
//            }
//        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private ContactEntity paramAccountEntityCreateRequest(String name, String email, String phone){
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setName(name);
        contactEntity.setEmail(email);
        contactEntity.setPhone(phone);
        return contactEntity;
    }

    
}
