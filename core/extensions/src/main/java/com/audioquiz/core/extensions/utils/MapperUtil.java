package com.audioquiz.core.extensions.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class MapperUtil {
    private static final ModelMapper modelMapper = ModelMapperUtil.getInstance();

    @Inject
    public MapperUtil() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    // Optionally, you can also define a method to map a collection
    public static <S, T> List<T> mapList(Collection<S> source, Class<T> targetClass) {
        return source.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
