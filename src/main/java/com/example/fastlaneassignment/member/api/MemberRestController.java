package com.example.fastlaneassignment.member.api;

import com.example.fastlaneassignment.auth.CurrentUser;
import com.example.fastlaneassignment.member.api.dto.MemberCreateRequestDto;
import com.example.fastlaneassignment.member.api.dto.MemberInfoResponseDto;
import com.example.fastlaneassignment.member.api.dto.MemberPasswordUpdateRequestDto;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.service.MemberApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.fastlaneassignment.common.ThrowUtils.hasErrorsThrow;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberApplicationService memberApplicationService;

    @PostMapping("/api/members/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberInfoResponseDto signUp(
            @RequestBody @Valid MemberCreateRequestDto requestDto,
            Errors errors
    ) {
        hasErrorsThrow(errors);
        Long memberId = memberApplicationService.signUp(requestDto.toServiceDto());
        Member member = memberApplicationService.getMember(memberId);
        return MemberInfoResponseDto.of(member);
    }

    @PutMapping("/api/members/{memberId}/passwords")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(
            @PathVariable Long memberId,
            @RequestBody @Valid MemberPasswordUpdateRequestDto requestDto,
            Errors errors,
            @CurrentUser Long currentUserId
    ) {
        hasErrorsThrow(errors);
        memberApplicationService.updatePassword(currentUserId, memberId, requestDto.toServiceDto());
    }

    @DeleteMapping("/api/members/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdrawal(
            @PathVariable Long memberId,
            @CurrentUser Long currentUserId
    ) {
        memberApplicationService.withdrawal(currentUserId, memberId);
    }
}
