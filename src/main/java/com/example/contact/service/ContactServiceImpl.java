package com.example.contact.service;

import com.example.contact.entities.ContactEntity;
import com.example.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    private ContactRepository contactRepository;
    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }
    @Override
    public Page<ContactEntity> findAllContact(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }

    @Override
    public Optional<ContactEntity> findById(Integer id) {
        return  contactRepository.findById(id);
    }

    @Override
    public void save(ContactEntity contactEntity) {
        contactRepository.save(contactEntity);
    }

    @Override
    public void remove(ContactEntity contactEntity) {
        contactRepository.delete(contactEntity);
    }

    @Override
    public List<ContactEntity> findByName(String name) {
        return contactRepository.findByName(name);
    }


}
