package com.audioquiz.data.remote.util.mapper;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.audioquiz.core.extensions.utils.ModelMapperUtil;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;

public class NetworkMapper {
    private static final String TAG = "DatabaseMapper";
    private static final ModelMapper modelMapper = ModelMapperUtil.getInstance();

    private NetworkMapper() {
        // Private constructor to prevent instantiation
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        Log.d(TAG, "map called: " + source.getClass().getSimpleName() + " to " + targetClass.getSimpleName());
        return modelMapper.map(source, targetClass);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public static <S, T> List<T> mapList(Collection<S> source, Class<T> targetClass) {
        return source.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }
}
