package com.example.fastlaneassignment.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountAdapter extends User {

    private Long currentUserId;


    public AccountAdapter(Long authUserId, String loginId, Collection<? extends GrantedAuthority> authorities) {
        super(loginId, "", authorities);
        this.currentUserId = authUserId;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }
}
