package com.example.contact.service;

import com.example.contact.entities.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Page<ContactEntity> findAllContact(Pageable pageable);

    Optional<ContactEntity> findById(Integer id);

    void save(ContactEntity contactEntity);

    void remove(ContactEntity contactEntity);

}
