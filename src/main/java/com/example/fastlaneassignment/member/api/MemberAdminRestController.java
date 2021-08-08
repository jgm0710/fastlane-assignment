package com.example.fastlaneassignment.member.api;

import com.example.fastlaneassignment.member.api.dto.MemberInfoResponseDto;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.service.MemberAdminApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberAdminRestController {

    private final MemberAdminApplicationService memberAdminApplicationService;

    @GetMapping("/api/admin/members")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberInfoResponseDto> findMemberListByAdmin() {
        List<Member> memberList = memberAdminApplicationService.findMemberList();
        return memberList.stream().map(MemberInfoResponseDto::of).collect(Collectors.toList());
    }
}
