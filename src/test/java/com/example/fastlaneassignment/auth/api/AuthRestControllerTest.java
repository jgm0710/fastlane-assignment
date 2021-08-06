package com.example.fastlaneassignment.auth.api;

import com.example.fastlaneassignment.auth.api.dto.OnlyRefreshTokenRequestDto;
import com.example.fastlaneassignment.auth.api.dto.SignInRequestDto;
import com.example.fastlaneassignment.auth.service.AuthApplicationService;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.common.BaseWebMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthRestController.class)
class AuthRestControllerTest extends BaseWebMvcTest {

    @MockBean
    AuthApplicationService authApplicationService;

    @Test
    @DisplayName("로그인")
    public void signIn() throws Exception {
        //given
        SignInRequestDto signInRequestDto = new SignInRequestDto("userHong", "userHong");

        TokenDto tokenDto = TokenDto.builder()
                .memberId(1L)
                .accessToken(MOCK_JWT_TOKEN)
                .accessTokenValidSeconds(3000)
                .refreshToken(UUID.randomUUID().toString())
                .refreshTokenValidSeconds(TimeUnit.DAYS.toSeconds(14))
                .build();

        given(authApplicationService.signIn(signInRequestDto.toServiceDto())).willReturn(tokenDto);

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/auth/sign-in")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isOk())
                .andDo(document(
                        "sign-in-success",
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description(JSON_CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("loginId").type(STRING).description("로그인 ID"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("memberId").type(NUMBER).description("인증된 회원 식별 ID"),
                                fieldWithPath("accessToken").type(STRING).description("인증 토큰"),
                                fieldWithPath("accessTokenValidSeconds").type(NUMBER).description("인증 토큰 유효 시간 (초)"),
                                fieldWithPath("refreshToken").type(STRING).description("인증 토큰 재발급을 위한 refresh token"),
                                fieldWithPath("refreshTokenValidSeconds").type(NUMBER).description("refresh token 유효 시간 (초)")
                        )
                ))
        ;

    }


    @Test
    @DisplayName("로그아웃")
    public void signOut() throws Exception {
        //given

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/auth/sign-out")
                        .header(ACCESS_TOKEN_HEADER, MOCK_JWT_TOKEN)
        ).andDo(print());

        //then
        perform
                .andExpect(status().isNoContent())
                .andDo(document(
                        "sign-out-success",
                        requestHeaders(
                                headerWithName(ACCESS_TOKEN_HEADER).description(ACCESS_TOKEN_HEADER_DESCRIPTION)
                        )
                ))
        ;
    }

    @Test
    @DisplayName("인증 토큰 재발급")
    public void refresh() throws Exception {
        //given
        OnlyRefreshTokenRequestDto onlyRefreshTokenRequestDto = new OnlyRefreshTokenRequestDto(UUID.randomUUID().toString());

        TokenDto tokenDto = TokenDto.builder()
                .memberId(1L)
                .accessToken(MOCK_JWT_TOKEN)
                .accessTokenValidSeconds(3000)
                .refreshToken(UUID.randomUUID().toString())
                .refreshTokenValidSeconds(TimeUnit.DAYS.toSeconds(14))
                .build();

        given(authApplicationService.refresh(onlyRefreshTokenRequestDto.getRefreshToken())).willReturn(tokenDto);

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/auth/refresh")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(onlyRefreshTokenRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isOk())
                .andDo(document(
                        "refresh-success",
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description(JSON_CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("refreshToken").type(STRING).description("인증 토큰 재발급을 위한 refresh token")
                        ),
                        responseFields(
                                fieldWithPath("memberId").type(NUMBER).description("인증된 회원 식별 ID"),
                                fieldWithPath("accessToken").type(STRING).description("인증 토큰"),
                                fieldWithPath("accessTokenValidSeconds").type(NUMBER).description("인증 토큰 유효 시간 (초)"),
                                fieldWithPath("refreshToken").type(STRING).description("인증 토큰 재발급을 위한 refresh token"),
                                fieldWithPath("refreshTokenValidSeconds").type(NUMBER).description("refresh token 유효 시간 (초)")
                        )
                ))
        ;

    }
}