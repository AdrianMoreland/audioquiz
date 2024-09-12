package com.audioquiz.localdatasource.mapper;

import android.util.Log;

import com.adrian.common.util.MapperUtil;

import javax.inject.Inject;

public class DatabaseMapper implements IDatabaseMapper {
    private final MapperUtil modelMapperUtil;

    @Inject
    public DatabaseMapper(MapperUtil modelMapperUtil) {
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public <T, S> T mapToDomain(S source, Class<T> domainClass) {
        Log.d("DatabaseMapper", "mapToDomain called");
        return modelMapperUtil.mapToDomain(source, domainClass);
    }

    @Override
    public <T, S> S mapFromDomain(T domain, Class<S> sourceClass) {
        Log.d("DatabaseMapper", "mapFromDomain called");
        return modelMapperUtil.mapFromDomain(domain, sourceClass);
    }
}
