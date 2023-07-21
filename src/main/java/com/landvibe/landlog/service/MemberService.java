package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.MemberJoinForm;
import com.landvibe.landlog.controller.form.MemberLoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(MemberJoinForm form) {
        validateNullMemberJoinForm(form);

        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> login(MemberLoginForm form) {
        return memberRepository.findByEmailWithPassword(form);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        validateNullCreatorId(memberId);
        Optional<Member> member = memberRepository.findById(memberId);
        validateNullMember(member);
        return member.get();
    }

    private void validateNullCreatorId(Long creatorId) {
        if (creatorId == null) {
            throw new IllegalArgumentException("creatorId가 없습니다!");
        }
    }

    private void validateNullMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다!");
        }
    }

    private void validateNullMemberJoinForm(MemberJoinForm form) {
        if (form == null || form.getName() == "" || form.getEmail() == "" || form.getPassword() == "") {
            throw new IllegalArgumentException("MemberJoinForm 객체가 null이거나, 필드가 올바르지 않습니다.");
        }
    }

}
