package com.example.semiproject;

import com.example.semiproject.entity.Member;
import com.example.semiproject.entity.Post;
import com.example.semiproject.entity.Role;
import com.example.semiproject.repository.MemberRepository;
import com.example.semiproject.repository.PostRepository;
import com.example.semiproject.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Dates;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){

        initService.doInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final MemberRepository memberRepository;
        private final PostRepository postRepository;
        private final RoleRepository roleRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;
        public void doInit(){
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
            Role role1 = new Role();
            role1.setName("ROLE_USER");
            roleRepository.save(role1);

            Member member = new Member();
            member.setName("홍길동");
            member.setEmail("admin@admin");
            member.setPassword(passwordEncoder.encode("admin"));
            member.addRole(role);
            member.addRole(role1);
            memberRepository.save(member);

            for(int i = 0 ; i < 30; i++){
                Post post = new Post(null,"테스트"+i,"테스트입니다." + i,false,member);
                postRepository.save(post);
            }

        }


    }

}
