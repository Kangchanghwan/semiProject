package com.example.semiproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {
    @GeneratedValue
    @Id
    @Column(name="role_id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<Member> members = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name;
    }
}
