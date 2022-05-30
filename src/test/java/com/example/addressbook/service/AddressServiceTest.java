package com.example.addressbook.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.addressbook.exception.ExceptionType;
import com.example.addressbook.mapper.AddressBookMapper;
import com.example.addressbook.model.entity.AddressBook;
import com.example.addressbook.model.request.AddressBookRequest;
import com.example.addressbook.model.response.AddressBookResponse;
import com.example.addressbook.repository.AddressRepository;
import com.example.addressbook.service.impl.AddressServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

  AddressService addressService;

  @Mock
  AddressRepository addressRepository;

  @Spy
  private AddressBookMapper mapper = Mappers.getMapper(AddressBookMapper.class);

  @BeforeEach
  public void initAll() {
    this.addressService = new AddressServiceImpl(addressRepository);
  }

  @Test
  void create() {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김호박").age(20).phone("01012345678")
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);
    when(addressRepository.save(any())).thenReturn(addressBook);

    // when
    AddressBookResponse result = addressService.create(addressBookRequest);

    // then
    verify(addressRepository, times(1)).save(addressBook);
    Assertions.assertEquals(result, mapper.toVo(addressBook));
  }

  @ParameterizedTest
  @CsvSource({
      "김호박, 21, 01012345678",
      "김호박A, 19, 01012345678",
      "김호박, 19, 0101234567899",
  })
  void createWithInvalidValue(String name, int age, String phone) {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().id(UUID.randomUUID()).name(name).age(age).phone(phone)
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);

    // when
    RuntimeException thrown = Assertions.assertThrows(IllegalArgumentException.class,
        () -> addressService.create(addressBookRequest));

    // then
    verify(addressRepository, times(0)).save(addressBook);
    Assertions.assertEquals(ExceptionType.VALIDATION_EXCEPTION.getMessage(), thrown.getMessage());
  }

  @Test
  void retrieve() {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김호박").age(20).phone("01012345678")
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);
    when(addressRepository.findById(any())).thenReturn(Optional.ofNullable(addressBook));

    // when
    AddressBookResponse result = addressService.retrieve(addressBookRequest.getId());

    // then
    verify(addressRepository, times(1)).findById(addressBookRequest.getId());
    Assertions.assertEquals(result, mapper.toVo(addressBook));
  }

  @Test
  void update() {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김호박").age(20).phone("01012345678")
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);
    when(addressRepository.findById(any())).thenReturn(Optional.ofNullable(addressBook));
    when(addressRepository.save(any())).thenReturn(addressBook);

    // when
    AddressBookResponse result =
        addressService.update(addressBookRequest.getId(), addressBookRequest);

    // then
    verify(addressRepository, times(1)).save(Objects.requireNonNull(addressBook));
    Assertions.assertEquals(result, mapper.toVo(addressBook));
  }

  @Test
  void cannotDeleteWithPhoneRegex() {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김호박").age(20).phone("01012345678")
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);
    when(addressRepository.findById(any())).thenReturn(Optional.ofNullable(addressBook));

    // when
    addressService.deleteWithPhoneRegex(addressBookRequest.getId(), "^02(\\d{7,9})$");

    // then
    verify(addressRepository, times(0)).deleteById(addressBookRequest.getId());
  }

  @Test
  void deleteWithPhoneRegex() {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김호박").age(20).phone("0212345678")
            .build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);
    when(addressRepository.findById(any())).thenReturn(Optional.ofNullable(addressBook));

    // when
    addressService.deleteWithPhoneRegex(addressBookRequest.getId(), "^02(\\d{7,9})$");

    // then
    verify(addressRepository, times(1)).deleteById(addressBookRequest.getId());
  }

  @Test
  void allList() {
    // given
    AddressBookRequest addressBookRequest = AddressBookRequest.builder().name("김호박").build();
    AddressBook addressBook = mapper.toEntity(addressBookRequest);
    List<AddressBook> bookList = new ArrayList<>();
    bookList.add(mapper.toEntity(
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김호박").age(20).phone("01012345678")
            .build()));
    bookList.add(mapper.toEntity(
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김감자").age(19).phone("021234567")
            .build()));
    Page<AddressBook> addressBookPage = new PageImpl<>(bookList);
    Example<AddressBook> example = Example.of(addressBook);
    when(addressRepository.findAll(any(), (Pageable) any())).thenReturn(addressBookPage);

    // when
    Page<AddressBookResponse> result =
        addressService.allList(addressBookRequest, Pageable.ofSize(2));

    // then
    verify(addressRepository, times(1)).findAll(example, Pageable.ofSize(2));
    Assertions.assertEquals(result, addressBookPage.map(mapper::toVo));
  }
}