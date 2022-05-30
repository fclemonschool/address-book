package com.example.addressbook.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * Exception에 대해서 응답 처리를 위한 Enum Type.
 */
@Getter
@ToString
@AllArgsConstructor
public enum ExceptionType {
  RUNTIME_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "E0001", "런타임 오류가 발생했습니다."),
  ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "E0002", "호출할 수 없습니다."),
  INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "E0003", "서버에 에러가 발생했습니다."),
  NO_SUCH_ELEMENT_EXCEPTION(HttpStatus.NOT_FOUND, "E0004", "해당하는 요소가 없습니다."),
  UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "A0001", "권한이 없습니다."),
  VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "V0001", "잘못된 입력값이 있습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;
}
