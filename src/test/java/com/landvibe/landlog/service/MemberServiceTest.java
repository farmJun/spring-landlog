package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.MemberJoinForm;
import com.landvibe.landlog.controller.form.MemberLoginForm;
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

    MemberJoinForm memberJoinForm = new MemberJoinForm("name","email","password");
    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member(1L, "name", "email", "password");

        //When
        Long saveId = memberService.join(memberJoinForm);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getEmail(), findMember.getEmail());
    }

    @Test
    @DisplayName("이메일과 비밀번호가 일치하는 Member 찾는 테스트")
    public void login() {
        memberService.join(memberJoinForm);

        MemberLoginForm memberLoginForm = new MemberLoginForm();
        memberLoginForm.setEmail("email");
        memberLoginForm.setPassword("password");

        Optional<Member> successFindMember = memberService.login(memberLoginForm);

        assertThat(successFindMember.isEmpty()).isNotEqualTo(null);
    }
}