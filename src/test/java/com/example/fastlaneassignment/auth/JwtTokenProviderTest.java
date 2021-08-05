package com.example.fastlaneassignment.auth;

import com.example.fastlaneassignment.config.SecurityJwtProperties;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.domain.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JwtTokenProviderTest {

    SecurityJwtProperties jwtProperties = mock(SecurityJwtProperties.class);

    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtProperties);

    @Test
    @DisplayName("jwt token 생성, 토큰을 통한 인증 정보 조회, 토큰 유효성 확인")
    public void createJwtToken() {
        //given
        Member member = Member.builder()
                .id(1L)
                .loginId("memberLoginId")
                .password("password")
                .roles(Set.of(MemberRole.USER))
                .build();

        given(jwtProperties.getSecretKey()).willReturn("temp");
        given(jwtProperties.getAccessTokenValidTime()).willReturn(10000L);

        String jwtToken = jwtTokenProvider.createToken(member.getId(), member.getLoginId(), member.getRoleNames());

        //when
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
        boolean valid = jwtTokenProvider.isValid(jwtToken);

        //then
        AccountAdapter accountAdapter = (AccountAdapter) authentication.getPrincipal();

        assertEquals(member.getId(), accountAdapter.getCurrentUserId());
        assertEquals(member.getLoginId(), accountAdapter.getUsername());

        Collection<GrantedAuthority> authorities = accountAdapter.getAuthorities();
        List<String> authoritiesString = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        authoritiesString.forEach(as -> assertTrue(member.getRoleNames().stream().anyMatch(as::contains),"회원의 권한 중 하나와 인증된 회원의 권한 중 하나가 일치"));

        assertTrue(valid, "토큰이 유효하다.");
    }

    @Test
    @DisplayName("토큰 유효성 확인 - 만료 날짜를 지난 경우")
    public void validateToken_Expired() {
        //given
        Member member = Member.builder()
                .id(1L)
                .loginId("memberLoginId")
                .password("password")
                .roles(Set.of(MemberRole.USER))
                .build();

        given(jwtProperties.getSecretKey()).willReturn("temp");
        given(jwtProperties.getAccessTokenValidTime()).willReturn(10000L);
        given(jwtProperties.getAccessTokenValidTime()).willReturn(-1000L);

        String jwtToken = jwtTokenProvider.createToken(member.getId(), member.getLoginId(), member.getRoleNames());

        //when
        boolean valid = jwtTokenProvider.isValid(jwtToken);

        //then
        assertFalse(valid);

    }

    @Test
    @DisplayName("토큰 유효성 확인 - 토큰이 null 인 경우")
    public void validateToken_NullToken() {
        //given

        //when
        boolean valid = jwtTokenProvider.isValid(null);

        //then
        assertFalse(valid);

    }

}