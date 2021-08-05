package com.example.fastlaneassignment.member.repository;

import com.example.fastlaneassignment.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByRefreshInfoRefreshToken(String refreshToken);

}
