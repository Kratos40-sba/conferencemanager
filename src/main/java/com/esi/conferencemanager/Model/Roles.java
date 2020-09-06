package com.esi.conferencemanager.Model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_ADMIN ,
    ROLE_AUTHOR ,
    ROLE_REVIEWER ,
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
