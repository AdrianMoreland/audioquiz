package com.audioquiz.core.extensions.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        // Optional: You can configure ModelMapper if needed (e.g., strict matching, property mappings)
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    // Private constructor to prevent instantiation
    private ModelMapperUtil() {
    }

    public static ModelMapper getInstance() {
        return modelMapper;
    }
}
