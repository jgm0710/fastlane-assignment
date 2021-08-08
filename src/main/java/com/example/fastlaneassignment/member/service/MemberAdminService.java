package com.example.fastlaneassignment.member.service;

import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.domain.MemberRole;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberAdminService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public List<Member> findMemberList() {
        return memberRepository.findAll();
    }

    public void createAdminMember() {
        String adminLoginId = "fastlaneAdmin";
        String encodingPassword = passwordEncoder.encode("fastlaneAdmin");

        if (memberRepository.findByLoginId(adminLoginId).isEmpty()) {
            Member admin = Member.builder()
                    .loginId(adminLoginId)
                    .password(encodingPassword)
                    .roles(Set.of(MemberRole.USER, MemberRole.ADMIN))
                    .build();

            memberRepository.save(admin);
        }
    }
}
