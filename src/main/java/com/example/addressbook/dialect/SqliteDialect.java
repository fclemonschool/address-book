package com.example.addressbook.dialect;

import java.sql.Types;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;

/**
 * SQLite에 대한 Dialect 구현체.
 * SQLite는 Dialect가 존재하지 않아서 생성.
 */
public class SqliteDialect extends Dialect {

  private static final String INTEGER = "integer";

  /**
   * 컬럼 타입에 대한 정보를 설정한다..
   */
  public SqliteDialect() {
    registerColumnType(Types.BIT, INTEGER);
    registerColumnType(Types.TINYINT, "tinyint");
    registerColumnType(Types.SMALLINT, "smallint");
    registerColumnType(Types.INTEGER, INTEGER);
    registerColumnType(Types.BIGINT, "bigint");
    registerColumnType(Types.FLOAT, "float");
    registerColumnType(Types.REAL, "real");
    registerColumnType(Types.DOUBLE, "double");
    registerColumnType(Types.NUMERIC, "numeric");
    registerColumnType(Types.DECIMAL, "decimal");
    registerColumnType(Types.CHAR, "char");
    registerColumnType(Types.VARCHAR, "varchar");
    registerColumnType(Types.LONGVARCHAR, "longvarchar");
    registerColumnType(Types.DATE, "date");
    registerColumnType(Types.TIME, "time");
    registerColumnType(Types.TIMESTAMP, "timestamp");
    registerColumnType(Types.BINARY, "blob");
    registerColumnType(Types.VARBINARY, "blob");
    registerColumnType(Types.LONGVARBINARY, "blob");
    registerColumnType(Types.BLOB, "blob");
    registerColumnType(Types.CLOB, "clob");
    registerColumnType(Types.BOOLEAN, INTEGER);
  }

  @Override
  public IdentityColumnSupport getIdentityColumnSupport() {
    return new SqliteIdentityColumnSupport();
  }

  @Override
  public boolean hasAlterTable() {
    return false;
  }

  @Override
  public boolean dropConstraints() {
    return false;
  }

  @Override
  public String getDropForeignKeyString() {
    return "";
  }

  @Override
  public String getAddForeignKeyConstraintString(String constraintName,
                                                 String[] foreignKey,
                                                 String referencedTable,
                                                 String[] primaryKey,
                                                 boolean referencesPrimaryKey) {
    return "";
  }

  @Override
  public String getAddPrimaryKeyConstraintString(String constraintName) {
    return "";
  }

  @Override
  public String getForUpdateString() {
    return "";
  }

  @Override
  public String getAddColumnString() {
    return "add column";
  }

  @Override
  public boolean supportsOuterJoinForUpdate() {
    return false;
  }

  @Override
  public boolean supportsIfExistsBeforeTableName() {
    return true;
  }

  @Override
  public boolean supportsCascadeDelete() {
    return false;
  }
}
