package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.MemberLoginForm;
import com.landvibe.landlog.domain.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();

	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	void save() {
		//given
		Member member = new Member();
		member.setEmail("spring");

		//when
		repository.save(member);

		//then
		Member result = repository.findById(member.getId()).get();
		assertThat(result).isEqualTo(member);
	}

	@Test
	public void findByName() {
		//given
		Member member1 = new Member();
		member1.setEmail("spring1");
		repository.save(member1);
		Member member2 = new Member();
		member2.setEmail("spring2");
		repository.save(member2);

		//when
		Member result = repository.findByName("spring1").get();

		//then
		assertThat(result).isEqualTo(member1);
	}

	@Test
	public void findAll() {
		//given
		Member member1 = new Member();
		member1.setEmail("spring1");
		repository.save(member1);
		Member member2 = new Member();
		member2.setEmail("spring2");
		repository.save(member2);

		//when
		List<Member> result = repository.findAll();

		//then
		assertThat(result.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("이메일과 비밀번호가 일치하는 Member 찾는 테스트")
	public void findByEmailWithPassword() {
		Member member = new Member();
		member.setEmail("123");
		member.setPassword("456");

		repository.save(member);

		MemberLoginForm memberLoginForm = new MemberLoginForm();
		memberLoginForm.setEmail("123");
		memberLoginForm.setPassword("456");

		Optional<Member> successFindMember = repository.findByEmailWithPassword(memberLoginForm);

		assertThat(member).isEqualTo(successFindMember.get());
		assertThat(successFindMember.isEmpty()).isNotEqualTo(null);
	}
}