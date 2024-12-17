package com.audioquiz.data.remote.util.mapper;

public interface INetworkMapper {
    <T, S> T mapToDomain(S source, Class<T> domainClass);
    <T, S> S mapFromDomain(T domain, Class<S> sourceClass);

}
