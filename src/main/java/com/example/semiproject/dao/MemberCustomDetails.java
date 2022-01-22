package com.example.semiproject.dao;

import com.example.semiproject.entity.Role;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberCustomDetails implements UserDetails,Serializable {

    private static final long serialVersionUID = 174726374856727L;

    private Long id;	// DB에서 PK 값
    private String password;	// 비밀번호
    private String email;	//이메일
    private String name; //닉네임
    private Collection<? extends GrantedAuthority> authorities;



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
