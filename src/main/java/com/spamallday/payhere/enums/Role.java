package com.spamallday.payhere.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER_OWNER("ROLE_USER_OWNER");

    private String value;
}
