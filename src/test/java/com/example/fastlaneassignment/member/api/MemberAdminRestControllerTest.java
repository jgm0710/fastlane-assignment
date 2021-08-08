package com.example.fastlaneassignment.member.api;

import com.example.fastlaneassignment.auth.service.AuthApplicationService;
import com.example.fastlaneassignment.auth.service.dto.SignInDto;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.common.BaseControllerTest;
import com.example.fastlaneassignment.common.BaseWebMvcTest;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.domain.MemberRole;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import com.example.fastlaneassignment.member.service.MemberAdminApplicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberAdminRestController.class)
class MemberAdminRestControllerTest extends BaseWebMvcTest {

    @MockBean
    MemberAdminApplicationService memberAdminApplicationService;

    @Test
    @DisplayName("회원 목록 조회")
    public void findMemberListByAdmin() throws Exception {
        //given
        List<Member> memberList = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Member member = Member.builder()
                    .id((long) i)
                    .loginId("userLoginId" + i)
                    .createDate(LocalDateTime.now())
                    .build();
            memberList.add(member);
        }

        given(memberAdminApplicationService.findMemberList()).willReturn(memberList);

        //when
        ResultActions perform = mockMvc.perform(
                get("/api/admin/members")
                        .header(ACCESS_TOKEN_HEADER, MOCK_JWT_TOKEN)
        ).andDo(print());

        //then
        perform
                .andExpect(status().isOk())
                .andDo(document(
                        "find-member-list-by-admin",
                        requestHeaders(
                                headerWithName(ACCESS_TOKEN_HEADER).description(ACCESS_TOKEN_HEADER_DESCRIPTION)
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("회원 식별 ID"),
                                fieldWithPath("[].loginId").description("로그인 ID"),
                                fieldWithPath("[].joinDate").description("가입 일시")
                        )
                ))
        ;

    }

}