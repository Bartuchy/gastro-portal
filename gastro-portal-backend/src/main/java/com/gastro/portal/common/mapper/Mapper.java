package com.gastro.portal.common.mapper;

@FunctionalInterface
public interface Mapper<S, T> {
    T map(S source);
}
