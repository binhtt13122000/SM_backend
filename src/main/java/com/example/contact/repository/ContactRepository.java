package com.example.contact.repository;

import com.example.contact.entities.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {
    @Query("select c from ContactEntity c where c.name like %?1%")
    List<ContactEntity> findByName(String name);
}
