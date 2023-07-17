package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.MemberLoginForm;
import com.landvibe.landlog.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    //변경 전 입니다.
    // 변경 후 입니다.
    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Optional<Member> findByEmailWithPassword(MemberLoginForm form);

    List<Member> findAll();
}
