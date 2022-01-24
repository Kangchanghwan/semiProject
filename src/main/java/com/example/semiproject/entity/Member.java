package com.example.semiproject.entity;

import com.example.semiproject.dao.form.MemberJoinForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private LocalDateTime remove_date;
    private Boolean enabled = Boolean.TRUE;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "member_role",
                joinColumns = @JoinColumn(name="member_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    public void mappingJoinMember(MemberJoinForm form){
        this.name = form.getName();
        this.email = form.getEmail();
        this.password = form.getPassword();
        this.phone = form.getPhone();
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

}
