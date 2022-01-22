package com.example.semiproject.config;

import com.example.semiproject.repository.MemberRepository;
import com.example.semiproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/post","/css/*","/account/*","/api/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .defaultSuccessUrl("/post")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService);
    }

/*    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select email,password,enabled "
//                        + "from member "
//                        + "where email = ?")// 인증처리
//                .authoritiesByUsernameQuery("select u.email ,r.name "
//                        + "from member_role ur inner join member u on ur.member_id = u.member_id "
//                        + "inner join role r on ur.role_id = r.role_id "
//                        + "where u.email = ?"); //권한처리
        .and()
                .jdbcAuthentication().authoritiesByUsernameQuery("select u.email ,r.name "
                        + "from member_role ur inner join member u on ur.member_id = u.member_id "
                        + "inner join role r on ur.role_id = r.role_id "
                        + "where u.email = ?");
    }*/



}
