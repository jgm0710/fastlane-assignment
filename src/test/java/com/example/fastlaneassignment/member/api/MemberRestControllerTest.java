package com.example.fastlaneassignment.member.api;

import com.example.fastlaneassignment.auth.service.AuthApplicationService;
import com.example.fastlaneassignment.common.BaseWebMvcTest;
import com.example.fastlaneassignment.member.api.dto.MemberCreateRequestDto;
import com.example.fastlaneassignment.member.service.MemberApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        //when
        ResultActions perform = mockMvc.perform(
                post("/api/members/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
        ).andDo(print());

        //then
        perform
                .andExpect(status().isCreated())
                .andDo(document(
                        "sign-up-success"
                ))
        ;

    }

}