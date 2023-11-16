package com.geneyi.domain.member;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER( "USER", "ROLE_USER", "일반 사용자"),
    ADMIN("ADMIN", "ROLE_ADMIN", "관리자");

    private final String role;
    private final String authority;
    private final String title;

}
