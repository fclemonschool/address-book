package com.example.addressbook.mapper;

import com.example.addressbook.model.entity.AddressBook;
import com.example.addressbook.model.request.AddressBookRequest;
import com.example.addressbook.model.response.AddressBookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct 라이브러리를 통해 Dto, Entity, Vo 사이의 변환을 처리한다.
 */
@Mapper(componentModel = "spring")
public interface AddressBookMapper
    extends EntityMapper<AddressBookRequest, AddressBookResponse, AddressBook> {
  AddressBookMapper INSTANCE = Mappers.getMapper(AddressBookMapper.class);
}
