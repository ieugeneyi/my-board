package com.geneyi.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userid;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role roles;

    @PrePersist
    private void prePersist() {
        this.roles = Role.USER;
    }

    @Builder
    public Member(String userid, String pw, String username, Role roles, PasswordEncoder encoder) {
        this.userid = userid;
        this.pw = encoder.encode(pw);
        this.username = username;
        this.roles = roles;
    }



}
