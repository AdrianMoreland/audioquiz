package com.audioquiz.designsystem.util;

import org.modelmapper.ModelMapper;


public class UiMapper<S, T> {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, T> S mapToDomain(T uiModel, Class<S> domainClass) {
        return modelMapper.map(uiModel, domainClass);
    }

    public static <S, T> T mapToUi(S domain, Class<T> uiModelClass) {
        return modelMapper.map(domain, uiModelClass);
    }
}