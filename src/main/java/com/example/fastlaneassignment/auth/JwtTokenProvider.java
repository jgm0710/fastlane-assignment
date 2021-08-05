package com.example.fastlaneassignment.auth;

import com.example.fastlaneassignment.config.SecurityJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final SecurityJwtProperties jwtProperties;

    private final String AUTH_USER_ID_KEY = "id";

    private final String AUTHORITIES_KEY = "roles";

    public String createToken(Long memberId, String loginId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(loginId); // JWT payload 에 저장되는 정보단위
        claims.put(AUTH_USER_ID_KEY, memberId);
        claims.put(AUTHORITIES_KEY, roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenValidTime())) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Long currentUserId = getCurrentUserId(token);
        String email = getLoginId(token);
        List<GrantedAuthority> authorities = getAuthorities(token);

        UserDetails userDetails = new AccountAdapter(currentUserId, email, authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Long getCurrentUserId(String token) {
        Claims claims = getClaims(token);
        return Long.valueOf(claims.get(AUTH_USER_ID_KEY).toString());
    }

    // 토큰에서 회원 정보 추출
    private String getLoginId(String token) {
        return getClaims(token).getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(jwtProperties.getTokenHeader());
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean isValid(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = getClaims(token);

        String[] authorities = claims.get(AUTHORITIES_KEY).toString().split(",");

        return Arrays.stream(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }
}
