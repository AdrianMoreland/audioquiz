package com.audioquiz.data.remote.dto;
import androidx.annotation.NonNull;

import java.util.UUID;

public class Id {

    private final String value;

    public static final Id UNKNOWN = new Id("UNKNOWN");

    public Id(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public static Id existing(@NonNull String value) {
        return new Id(value);
    }

    @NonNull
    public static Id createNew() {
        return new Id(UUID.randomUUID().toString());
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public static Id orUnknown(@NonNull Id id) {
        return id != null ? id : UNKNOWN;
    }

}