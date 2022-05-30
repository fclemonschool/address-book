package com.example.addressbook.controller;

import com.example.addressbook.model.request.AddressBookRequest;
import com.example.addressbook.model.response.AddressBookResponse;
import com.example.addressbook.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * AddressBook에 대한 Controller.
 */
@Tag(name = "Addresses", description = "주소록 API")
@RestController
@RequestMapping(value = "/api/v1/addresses")
public class AddressBookController {

  private final AddressService addressService;

  public AddressBookController(AddressService addressService) {
    this.addressService = addressService;
  }

  /**
   * 새로운 주소록을 생성한다.
   *
   * @param addressBookRequest 주소록 Dto
   * @return 생성한 주소록 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "주소록 생성", description = "새로운 주소록을 생성한다.")
  @PostMapping()
  public ResponseEntity<AddressBookResponse> create(
      @RequestBody @Valid AddressBookRequest addressBookRequest) {
    AddressBookResponse result = addressService.create(addressBookRequest);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(result.getId()).toUri()).body(result);
  }

  /**
   * 입력받은 아이디와 일치하는 주소록을 조회한다.
   *
   * @param id 조회할 주소록 id
   * @return 조회한 주소록 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "주소록 조회", description = "입력받은 아이디와 일치하는 주소록을 조회한다.")
  @GetMapping(value = "/{id}")
  public ResponseEntity<AddressBookResponse> retrieve(@PathVariable UUID id) {
    return ResponseEntity.ok(addressService.retrieve(id));
  }

  /**
   * 입력받은 아이디와 일치하는 주소록을 입력받은 정보로 수정한다.
   *
   * @param id                 수정할 주소록 id
   * @param addressBookRequest 수정할 주소록 Dto
   * @return 업데이트한 주소록 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "주소록 수정", description = "입력받은 아이디와 일치하는 주소록을 입력받은 정보로 수정한다.")
  @PutMapping("/{id}")
  public ResponseEntity<AddressBookResponse> update(@PathVariable UUID id,
                                                    @RequestBody
                                                    AddressBookRequest addressBookRequest) {
    return ResponseEntity.ok(addressService.update(id, addressBookRequest));
  }

  /**
   * 입력받은 아이디와 일치하는 주소록(02로 시작하는 최대 11자리)을 삭제한다.
   *
   * @param id 삭제할 주소록 id
   * @return 컨텐츠 없음 응답
   */
  @Operation(summary = "주소록 삭제", description = "입력받은 아이디와 일치하는 주소록(02로 시작하는 최대 11자리)을 삭제한다.")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> delete(@PathVariable UUID id) {
    addressService.deleteWithPhoneRegex(id, "^02(\\d{7,9})$");
    return ResponseEntity.noContent().build();
  }

  /**
   * 입력받은 페이징 정보와 Dto 객체에 담긴 필터 정보를 적용하여 해당하는 전체 주소록을 조회한다.
   * size: 페이지 사이즈
   * number: 조회할 페이지(0부터 시작)
   * sort: 정렬 대상 및 방법
   * ex) http://localhost:8080/api/v1/addresses?size=3&number=0&sort=name,desc
   *
   * @param addressBookRequest 주소록 Dto
   * @param pageable           페이징 정보(sort, page, size)
   * @return 페이징 처리, 필터 처리한 주소록 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "주소록 목록 조회",
      description = "입력받은 페이징 정보와 Dto 객체에 담긴 필터 정보를 적용하여 해당하는 전체 주소록을 조회한다.")
  @GetMapping()
  public ResponseEntity<Page<AddressBookResponse>> list(AddressBookRequest addressBookRequest,
                                                        Pageable pageable) {
    return ResponseEntity.ok(addressService.allList(addressBookRequest, pageable));
  }
}
