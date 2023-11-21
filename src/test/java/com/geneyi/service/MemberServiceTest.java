package com.geneyi.service;

import com.geneyi.domain.member.Member;
import com.geneyi.domain.member.MemberRepository;
import com.geneyi.dto.member.MemberRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp(){
        memberRepository.deleteAll();
    }

    @Transactional
    @Test
    public void member_join() throws Exception {
        //given
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .userid("test")
                .pw("test")
                .username("testname")
                .build();

        //when
        Long joined = memberService.join(requestDto);

        //then
        Member member = memberRepository.findById(joined).orElse(null);
        assert member != null;
        assertThat(member.getUsername()).isEqualTo("testname");
    }

    @Transactional
    @Test
    public void member_duplicate_test() throws Exception{
        //given
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .userid("test")
                .pw("test")
                .username("testname")
                .build();

        MemberRequestDto requestDto1 = MemberRequestDto.builder()
                .userid("test")
                .pw("test")
                .username("testname2")
                .build();

        //when
        memberService.join(requestDto);

        //then
        assertThatThrownBy(() -> memberService.join(requestDto1))
                .isInstanceOf(IllegalStateException.class);

    }

}