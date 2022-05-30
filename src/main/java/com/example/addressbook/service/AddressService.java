package com.example.addressbook.service;

import com.example.addressbook.model.request.AddressBookRequest;
import com.example.addressbook.model.response.AddressBookResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 주소록 서비스 인터페이스.
 */
public interface AddressService {

  /**
   * 새로운 주소록을 생성한다.
   *
   * @param addressBookRequest 주소록 Dto
   * @return 생성한 주소록 Vo
   */
  AddressBookResponse create(AddressBookRequest addressBookRequest);

  /**
   * 입력받은 아이디와 일치하는 주소록을 조회한다.
   *
   * @param id 조회할 주소록 id
   * @return 조회한 주소록 Vo
   */
  AddressBookResponse retrieve(UUID id);

  /**
   * 입력받은 아이디와 일치하는 주소록을 입력받은 정보로 수정한다.
   *
   * @param id                 수정할 주소록 id
   * @param addressBookRequest 수정할 주소록 Dto
   * @return 업데이트한 주소록 Vo
   */
  AddressBookResponse update(UUID id, AddressBookRequest addressBookRequest);

  /**
   * 입력받은 아이디, 정규 표현식과 일치하는 주소록을 삭제한다.
   *
   * @param id    삭제할 주소록 id
   * @param regex 전화번호를 삭제할 때 사용할 정규 표현식
   */
  void deleteWithPhoneRegex(UUID id, String regex);

  /**
   * 입력받은 페이징 정보와 Dto 객체에 담긴 필터 정보를 적용하여 해당하는 전체 주소록을 조회한다.
   *
   * @param addressBookRequest 주소록 Dto
   * @param pageable           페이징 정보
   * @return 페이징 처리, 필터 처리한 주소록 Vo
   */
  Page<AddressBookResponse> allList(AddressBookRequest addressBookRequest, Pageable pageable);
}
