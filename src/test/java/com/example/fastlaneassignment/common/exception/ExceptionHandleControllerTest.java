package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.auth.api.dto.OnlyRefreshTokenRequestDto;
import com.example.fastlaneassignment.auth.api.dto.SignInRequestDto;
import com.example.fastlaneassignment.auth.service.AuthService;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.member.api.dto.MemberCreateRequestDto;
import com.example.fastlaneassignment.member.api.dto.MemberPasswordUpdateRequestDto;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ExceptionHandleControllerTest  extends BaseBootControllerTest{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("BadRequestException 처리")
    public void badRequestExceptionHandling() throws Exception {
        //given
        String encodingPassword = passwordEncoder.encode("password");
        Member user = Member.createUser("loginId", encodingPassword);
        memberRepository.save(user);

        SignInRequestDto signInRequestDto = new SignInRequestDto(user.getLoginId(), "anonymousPassword");

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("message").exists())
        ;

    }

    @Test
    @DisplayName("UnauthorizedException 처리")
    public void unauthorizedExceptionHandling() throws Exception {
        //given
        OnlyRefreshTokenRequestDto onlyRefreshTokenRequestDto = new OnlyRefreshTokenRequestDto("wjfeowijf");

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(onlyRefreshTokenRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @DisplayName("ForbiddenException 처리")
    public void forbiddenExceptionHandling() throws Exception {
        //given
        Member user = Member.createUser("loginId", "password");
        memberRepository.save(user);

        TokenDto tokenDto = authService.initAuthToken(user);

        MemberPasswordUpdateRequestDto memberPasswordUpdateRequestDto = new MemberPasswordUpdateRequestDto(user.getPassword(), "newPassword");

        //when
        ResultActions perform = mockMvc.perform(
                put("/api/members/{memberId}/passwords", 10000L)
                        .header("X-AUTH-TOKEN", tokenDto.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPasswordUpdateRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("message").exists())
        ;
    }

    @Test
    @DisplayName("NotFoundException 처리")
    public void notFoundExceptionHandling() throws Exception {
        //given
        String encodingPassword = passwordEncoder.encode("password");
        Member user = Member.createUser("loginId", encodingPassword);
        memberRepository.save(user);

        SignInRequestDto signInRequestDto = new SignInRequestDto("anonymousUser", "anonymousPassword");

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("message").exists())
        ;

    }

    @Test
    @DisplayName("ConflictException 처리")
    public void conflictExceptionHandling() throws Exception {
        //given
        Member user = Member.createUser("loginId", "password");
        memberRepository.save(user);

        MemberCreateRequestDto memberCreateRequestDto = new MemberCreateRequestDto(user.getLoginId(), user.getPassword());

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/members/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isConflict())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("message").exists());

    }

    @Test
    @DisplayName("validationHasErrorException 처리")
    public void validationHasErrorExceptionHandling() throws Exception {
        //given
        MemberCreateRequestDto memberCreateRequestDto = new MemberCreateRequestDto("", "");

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/members/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data[0].objectName").exists())
                .andExpect(jsonPath("data[0].code").exists())
                .andExpect(jsonPath("data[0].defaultMessage").exists())
                .andExpect(jsonPath("data[0].field").exists())
                .andExpect(jsonPath("message").exists());

    }
}