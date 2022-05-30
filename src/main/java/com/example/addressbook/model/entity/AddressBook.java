package com.example.addressbook.model.entity;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DB에서 사용하기 위한 주소록 Entity.
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
/*
  @Data 또는 @EqualsAndHashCode를 @Entity에 적용시 성능 저하 발생할 수 있다고 하여 미적용
 */
public class AddressBook {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer age;

  @Column(nullable = false, length = 11)
  private String phone;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AddressBook that = (AddressBook) o;

    if (!Objects.equals(id, that.id)) {
      return false;
    }
    if (!Objects.equals(name, that.name)) {
      return false;
    }
    if (!Objects.equals(age, that.age)) {
      return false;
    }
    return Objects.equals(phone, that.phone);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (age != null ? age.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    return result;
  }
}
