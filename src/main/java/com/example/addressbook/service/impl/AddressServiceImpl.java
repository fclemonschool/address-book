package com.example.addressbook.service.impl;

import com.example.addressbook.exception.ExceptionType;
import com.example.addressbook.mapper.AddressBookMapper;
import com.example.addressbook.model.entity.AddressBook;
import com.example.addressbook.model.request.AddressBookRequest;
import com.example.addressbook.model.response.AddressBookResponse;
import com.example.addressbook.repository.AddressRepository;
import com.example.addressbook.service.AddressService;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 주소록 서비스 구현체.
 */
@Service
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  /**
   * 새로운 주소록을 생성한다.
   *
   * @param addressBookRequest 주소록 Dto
   * @return 생성한 주소록 Vo
   */
  @Transactional
  @Override
  public AddressBookResponse create(AddressBookRequest addressBookRequest) {
    if (addressBookRequest.getAge() <= 20 && addressBookRequest.getName().matches("[가-힣]+")
        && addressBookRequest.getPhone().matches("^(\\d{9,11})$")) {
      return AddressBookMapper.INSTANCE.toVo(
          addressRepository.save(AddressBookMapper.INSTANCE.toEntity(addressBookRequest))
      );
    }
    throw new IllegalArgumentException(ExceptionType.VALIDATION_EXCEPTION.getMessage());
  }

  /**
   * 입력받은 아이디와 일치하는 주소록을 조회한다.
   *
   * @param id 조회할 주소록 id
   * @return 조회한 주소록 Vo
   */
  @Override
  public AddressBookResponse retrieve(UUID id) {
    Optional<AddressBook> addressBook = addressRepository.findById(id);
    if (addressBook.isPresent()) {
      return AddressBookMapper.INSTANCE.toVo(addressBook.get());
    }
    throw new NoSuchElementException(ExceptionType.NO_SUCH_ELEMENT_EXCEPTION.getMessage());
  }

  /**
   * 입력받은 아이디와 일치하는 주소록을 입력받은 정보로 수정한다.
   *
   * @param id                 수정할 주소록 id
   * @param addressBookRequest 수정할 주소록 Dto
   * @return 업데이트한 주소록 Vo
   */
  @Transactional
  @Override
  public AddressBookResponse update(UUID id, AddressBookRequest addressBookRequest) {
    retrieve(id);
    return AddressBookMapper.INSTANCE.toVo(
        addressRepository.save(AddressBookMapper.INSTANCE.toEntity(addressBookRequest)));
  }

  /**
   * 입력받은 아이디, 정규 표현식과 일치하는 주소록을 삭제한다.
   *
   * @param id    삭제할 주소록 id
   * @param regex 전화번호를 삭제할 때 사용할 정규 표현식
   */
  @Override
  public void deleteWithPhoneRegex(UUID id, String regex) {
    AddressBookResponse addressBook = retrieve(id);
    if (addressBook.getPhone().matches(regex)) {
      delete(id);
    }
  }

  /**
   * 입력받은 페이징 정보와 Dto 객체에 담긴 필터 정보를 적용하여 해당하는 전체 주소록을 조회한다.
   *
   * @param addressBookRequest 주소록 Dto
   * @param pageable           페이징 정보
   * @return 페이징 처리, 필터 처리한 주소록 Vo
   */
  @Override
  public Page<AddressBookResponse> allList(AddressBookRequest addressBookRequest,
                                           Pageable pageable) {
    Example<AddressBook> example =
        Example.of(AddressBookMapper.INSTANCE.toEntity(addressBookRequest));
    return addressRepository.findAll(example, pageable)
        .map(AddressBookMapper.INSTANCE::toVo);
  }

  /**
   * 입력받은 아이디에 대한 주소록을 삭제한다.
   *
   * @param id 삭제할 대상 아이디
   */
  @Transactional
  public void delete(UUID id) {
    addressRepository.deleteById(id);
  }
}
