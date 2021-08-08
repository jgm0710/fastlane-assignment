package com.example.fastlaneassignment.member.service;

import com.example.fastlaneassignment.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAdminApplicationService {

    private final MemberAdminService memberAdminService;

    public List<Member> findMemberList() {
        return memberAdminService.findMemberList();
    }

}
