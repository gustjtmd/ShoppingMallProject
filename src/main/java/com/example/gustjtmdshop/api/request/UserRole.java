package com.example.gustjtmdshop.api.request;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    admin("관리자"),
    member("사용자");


    private final String title;

    public static UserRole of(String title) {
        return Arrays.stream(values())
                .filter(v -> v.getTitle().equals(title))
                .findFirst().orElseThrow(() -> new RuntimeException(title));
    }

    public String getCode() {
        return name();
    }
}

