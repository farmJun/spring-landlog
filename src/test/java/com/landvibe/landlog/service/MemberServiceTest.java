package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.MemberLoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setEmail("hello");
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getEmail(), findMember.getEmail());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setEmail("spring");
        Member member2 = new Member();
        member2.setEmail("spring");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("이메일과 비밀번호가 일치하는 Member 찾는 테스트")
    public void login() {
        Member member = new Member();
        member.setEmail("123");
        member.setPassword("456");

        memberService.join(member);

        MemberLoginForm memberLoginForm = new MemberLoginForm();
        memberLoginForm.setEmail("123");
        memberLoginForm.setPassword("456");

        Optional<Member> successFindMember = memberService.login(memberLoginForm);

        assertThat(successFindMember.isEmpty()).isNotEqualTo(null);
        assertThat(member).isEqualTo(successFindMember.get());
    }
}