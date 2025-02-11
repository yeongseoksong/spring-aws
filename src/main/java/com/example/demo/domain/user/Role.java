package com.example.demo.domain.user;

import lombok.Getter;

@Getter

public enum Role {

    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","일반 사용자");

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }

    private final String key;
    private final String title;
}
