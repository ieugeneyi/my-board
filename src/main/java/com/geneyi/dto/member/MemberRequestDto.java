package com.geneyi.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberRequestDto {

    private String userid;
    private String username;
    private String pw;

}
