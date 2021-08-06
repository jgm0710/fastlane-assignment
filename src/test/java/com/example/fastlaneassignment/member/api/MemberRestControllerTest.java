package com.example.fastlaneassignment.member.api;

import com.example.fastlaneassignment.auth.service.AuthApplicationService;
import com.example.fastlaneassignment.common.BaseWebMvcTest;
import com.example.fastlaneassignment.member.api.dto.MemberCreateRequestDto;
import com.example.fastlaneassignment.member.api.dto.MemberPasswordUpdateRequestDto;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.domain.MemberRole;
import com.example.fastlaneassignment.member.service.MemberApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberRestController.class)
class MemberRestControllerTest extends BaseWebMvcTest {

    @MockBean
    MemberApplicationService memberApplicationService;

    @MockBean
    AuthApplicationService authApplicationService;

    @Test
    @DisplayName("회원 가입")
    public void signUp() throws Exception {
        //given
        MemberCreateRequestDto memberCreateRequestDto = new MemberCreateRequestDto("userHong", "userHong123");

        Member member = Member.builder()
                .id(1L)
                .loginId(memberCreateRequestDto.getLoginId())
                .password(memberCreateRequestDto.getPassword())
                .roles(Set.of(MemberRole.USER))
                .createDate(LocalDateTime.of(2021, 8, 6, 3, 17, 32, 1))
                .build();

        given(memberApplicationService.signUp(memberCreateRequestDto.toServiceDto())).willReturn(member.getId());
        given(memberApplicationService.getMember(member.getId())).willReturn(member);

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/members/sign-up")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isCreated())
                .andDo(document(
                        "sign-up-success",
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description(JSON_CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("loginId").type(STRING).description("로그인 ID"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("회원 식별 ID"),
                                fieldWithPath("loginId").type(STRING).description("로그인 ID"),
                                fieldWithPath("joinDate").type(STRING).description("가입 일시")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("비밀번호 변경")
    public void updatePassword() throws Exception {
        //given
        MemberPasswordUpdateRequestDto memberPasswordUpdateRequestDto = new MemberPasswordUpdateRequestDto("oldPassword", "newPassword");

        //when
        ResultActions perform = mockMvc.perform(
                put("/api/members/{memberId}/passwords", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPasswordUpdateRequestDto))
                        .header(ACCESS_TOKEN_HEADER, MOCK_JWT_TOKEN)
        ).andDo(print());

        //then
        perform
                .andExpect(status().isNoContent())
                .andDo(document(
                        "update-password-success",
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description(JSON_CONTENT_TYPE_DESCRIPTION),
                                headerWithName(ACCESS_TOKEN_HEADER).description(ACCESS_TOKEN_HEADER_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("oldPassword").type(STRING).description("본인 확인을 위한 기존 비밀번호"),
                                fieldWithPath("newPassword").type(STRING).description("변경할 새로운 비밀번호")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("회원탈퇴")
    public void withdrawal() throws Exception {
        //given

        //when
        ResultActions perform = mockMvc.perform(
                delete("/api/members/{memberId}", 1L)
                        .header(ACCESS_TOKEN_HEADER, MOCK_JWT_TOKEN)
        ).andDo(print());

        //then
        perform
                .andExpect(status().isNoContent())
                .andDo(document(
                        "withdrawal-success",
                        requestHeaders(
                                headerWithName(ACCESS_TOKEN_HEADER).description(ACCESS_TOKEN_HEADER_DESCRIPTION)
                        )
                ))
        ;

    }

}