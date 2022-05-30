package com.example.addressbook.mapper;

/**
 * Dto, Entity, Vo 사이의 변환을 위한 인터페이스.
 *
 * @param <D> Dto
 * @param <V> Vo
 * @param <E> Entity
 */
public interface EntityMapper<D, V, E> {
  E toEntity(final D dto);

  V toVo(final E entity);

  D toDto(final E entity);
}
