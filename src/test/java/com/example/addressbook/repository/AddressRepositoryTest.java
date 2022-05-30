package com.example.addressbook.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.addressbook.mapper.AddressBookMapper;
import com.example.addressbook.model.entity.AddressBook;
import com.example.addressbook.model.request.AddressBookRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressRepositoryTest {

  @Autowired
  private AddressRepository addressRepository;

  @Spy
  private AddressBookMapper mapper = Mappers.getMapper(AddressBookMapper.class);

  @Test
  void saveAndFindById() {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().name("김호박").age(20).phone("01012345678")
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);

    // when
    AddressBook result = addressRepository.save(addressBook);

    // then
    assertTrue(addressRepository.findById(result.getId()).isPresent());
  }

  @Test
  void findAll() {
    // given
    List<AddressBookRequest> requestList = new ArrayList<>();
    requestList.add(AddressBookRequest.builder().name("김호박").age(20).phone("01012345678")
        .build());
    requestList.add(AddressBookRequest.builder().name("김감자").age(21).phone("021234567")
        .build());
    List<AddressBook> entityList =
        requestList.stream().map(mapper::toEntity).collect(Collectors.toList());

    // when
    addressRepository.saveAll(entityList);

    // then
    assertTrue(addressRepository.findAll(Example.of(new AddressBook()), Pageable.ofSize(10))
        .getTotalElements() > 1);
  }

  @Test
  void deleteById() {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().name("김호박").age(20).phone("01012345678")
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);
    AddressBook result = addressRepository.save(addressBook);

    // when
    addressRepository.deleteById(addressBook.getId());

    // then
    assertFalse(addressRepository.findById(result.getId()).isPresent());
  }
}
