package com.security.module.common;

public interface Mapper {
    <T, K> T dtoToEntity(K dto);

    <K, T> K entityToDto(T entity);
}
