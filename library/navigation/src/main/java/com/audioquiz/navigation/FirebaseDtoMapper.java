package com.audioquiz.navigation;
/*

import com.audioquiz.data.remote.dto.UserStatsDto;
import com.google.firebase.firestore.DocumentSnapshot;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class FirebaseDtoMapper<T> implements FirebaseModelMapper<T> {
    private final ModelMapper modelMapper;

    @Inject
    public FirebaseDtoMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public UserStatsDto mapFromSnapshot(DocumentSnapshot snapshot) {
        Map<String, Object> snapshotData = snapshot.getData();
        return modelMapper.map(snapshotData, UserStatsDto.class);
    }

     @SuppressWarnings("unchecked")
    public Map<String, Object> mapToFieldsMap(T dto) {
         return modelMapper.map(dto, HashMap.class);
    }


}*/