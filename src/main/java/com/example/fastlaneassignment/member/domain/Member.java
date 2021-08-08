package com.example.fastlaneassignment.member.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    @ElementCollection(targetClass = MemberRole.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "member_role", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    @Embedded
    private RefreshInfo refreshInfo;

    @CreationTimestamp
    private LocalDateTime createDate;

    @Builder
    public Member(Long id, String loginId, String password, Set<MemberRole> roles, RefreshInfo refreshInfo, LocalDateTime createDate) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.roles = roles;
        this.refreshInfo = refreshInfo;
        this.createDate = createDate;
    }

    public static Member createUser(String loginId, String password) {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .roles(Set.of(MemberRole.USER))
                .refreshInfo(null)
                .build();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public List<String> getRoleNames() {
        return roles.stream().map(MemberRole::getRoleName).collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    public void expireRefreshToken() {
        this.refreshInfo = null;
    }

    public boolean validateRefreshToken() {
        return refreshInfo.isValid();
    }

    public RefreshInfo getRefreshInfo() {
        return refreshInfo;
    }

    public Set<MemberRole> getRoles() {
        return roles;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setRefreshInfo(RefreshInfo refreshInfo) {
        this.refreshInfo = refreshInfo;
    }
}
