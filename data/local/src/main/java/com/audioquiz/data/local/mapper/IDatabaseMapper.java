package com.audioquiz.data.local.mapper;

public interface IDatabaseMapper {
    <T, S> T mapToDomain(S source, Class<T> domainClass);
    <T, S> S mapFromDomain(T domain, Class<S> sourceClass);
}
