package com.example.chatserver.dto;

import com.example.chatserver.dto.MongoUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class AuthUser implements UserDetails {
    private String name;
    private String password;
//    private UUID uuid = UUID.randomUUID();

    public AuthUser(MongoUser u){
        this.name = u.getUsername();
        this.password = u.getPassword();
//        this.uuid = u.getUuid();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public UUID getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }

    @Override
    public String toString(){
        return this.name;// + "["+this.uuid.toString()+"]";
    }
}
