package com.example.addressbook.model.response;

import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 주소록 Vo.
 */
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class AddressBookResponse {
  private UUID id;

  private String name;

  private Integer age;

  private String phone;
}
