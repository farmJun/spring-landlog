package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.form.MemberLoginForm;
import com.landvibe.landlog.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Optional<Member> findByEmailWithPassword(MemberLoginForm form);

    List<Member> findAll();
}
