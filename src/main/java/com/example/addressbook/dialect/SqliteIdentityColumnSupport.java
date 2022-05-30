package com.example.addressbook.dialect;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

/**
 * SQLite의 Identity 컬럼에 대해 지원을 위해 생성.
 */
public class SqliteIdentityColumnSupport extends IdentityColumnSupportImpl {
  @Override
  public boolean supportsIdentityColumns() {
    return true;
  }

  @Override
  public String getIdentitySelectString(String table, String column, int type)
      throws MappingException {
    return "select last_insert_rowid()";
  }

  @Override
  public String getIdentityColumnString(int type) throws MappingException {
    return "integer";
  }
}
