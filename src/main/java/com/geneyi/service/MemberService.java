package com.geneyi.service;


import com.geneyi.domain.member.Member;
import com.geneyi.domain.member.MemberRepository;
import com.geneyi.dto.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long join(MemberRequestDto dto){
        //validation
        validateDuplicateMember(dto);
        //create
        Member member = Member.builder()
                .userid(dto.getUserid())
                .username(dto.getUsername())
                .pw(dto.getPw())
                .encoder(passwordEncoder)
                .build();
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(MemberRequestDto dto){
        memberRepository.findByUserid(dto.getUserid())
                .ifPresent( m -> {
                    throw new IllegalStateException("duplicate member exists");
                });
    }

}
