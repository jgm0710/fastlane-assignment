package com.example.fastlaneassignment.auth.api;

import com.example.fastlaneassignment.auth.CurrentUser;
import com.example.fastlaneassignment.auth.api.dto.OnlyRefreshTokenRequestDto;
import com.example.fastlaneassignment.auth.api.dto.SignInRequestDto;
import com.example.fastlaneassignment.auth.api.dto.TokenResponseDto;
import com.example.fastlaneassignment.auth.service.AuthApplicationService;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.member.service.MemberApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private AuthApplicationService authApplicationService;

    private MemberApplicationService memberApplicationService;

    @PostMapping("/api/auth/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto signIn(
            @RequestBody SignInRequestDto requestDto
    ) {
        TokenDto tokenDto = authApplicationService.signIn(requestDto.toServiceDto());

        return TokenResponseDto.of(tokenDto);
    }

    @GetMapping("/api/auth/sign-out")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(
            @CurrentUser Long currentUserId
    ) {
        authApplicationService.signOut(currentUserId);
    }

    @PostMapping("/api/auth/refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto refresh(
            @RequestBody OnlyRefreshTokenRequestDto requestDto
    ) {
        TokenDto tokenDto = authApplicationService.refresh(requestDto.getRefreshToken());

        return TokenResponseDto.of(tokenDto);
    }
}
