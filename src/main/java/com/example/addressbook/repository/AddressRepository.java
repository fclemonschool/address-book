package com.example.addressbook.repository;

import com.example.addressbook.model.entity.AddressBook;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * JPA를 통한 CRUD 등의 주소록 쿼리를 처리하는 Repository.
 */
public interface AddressRepository extends JpaRepository<AddressBook, UUID>,
    QueryByExampleExecutor<AddressBook> {
}
