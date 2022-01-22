package com.example.semiproject.service;

import com.example.semiproject.dao.MemberCustomDetails;
import com.example.semiproject.dao.form.MemberEditForm;
import com.example.semiproject.dao.form.MemberLoginForm;
import com.example.semiproject.entity.Member;
import com.example.semiproject.entity.Role;
import com.example.semiproject.repository.MemberRepository;
import com.example.semiproject.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;


    public Member save(Member member) {
        Role role = roleRepository.findById(2l).get();
        member.addRole(role);
        return memberRepository.save(member);
    }

    public void editMember(MemberEditForm form) {
        Authentication authentic = SecurityContextHolder.getContext().getAuthentication();
        String name = authentic.getName();
        Member member = memberRepository.findByEmail(name).get();
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        member.setPassword(encoder.encode(form.getPassword()));

    }

    public Member findByEmail(MemberLoginForm form) {
        Member findMember = memberRepository.findByEmail(form.getEmail()).orElse(null);
        return findMember;
    }

    public List<Member> members() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).get();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> opMember = memberRepository.findByEmail(email);

        if (opMember.isPresent()) {
            Member member = opMember.get();
            MemberCustomDetails authMember = new MemberCustomDetails();
            authMember.setEmail(member.getEmail());
            authMember.setAuthorities(member.getRoles());
            authMember.setPassword(member.getPassword());
            authMember.setId(member.getId());
            authMember.setName(member.getName());
            return authMember;
        }else{
            throw new UsernameNotFoundException("username Not found " + email);
        }
    }
}
