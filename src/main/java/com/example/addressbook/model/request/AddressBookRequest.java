package com.example.addressbook.model.request;

import java.util.UUID;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 주소록 Dto.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class AddressBookRequest {
  private UUID id;

  @Pattern(regexp = "[가-힣]+", message = "이름은 완성된 한글만 입력할 수 있습니다.")
  @NotEmpty
  private String name;

  @Max(value = 20)
  private Integer age;

  @Pattern(regexp = "^(\\d{9,11})$", message = "전화번호는 특수문자를 허용하지 않는 9-11자리 숫자입니다.")
  @NotEmpty
  private String phone;
}
