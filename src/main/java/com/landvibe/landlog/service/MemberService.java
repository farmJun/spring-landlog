package com.landvibe.landlog.service;

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

	public Long join(Member member) {
		validateDuplicateMember(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	public Optional<Member> login(String email, String password) {
		return memberRepository.findByEmailWithPassword(email, password);
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getEmail())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
