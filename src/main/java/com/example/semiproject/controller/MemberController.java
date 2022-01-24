package com.example.semiproject.controller;

import com.example.semiproject.dao.MemberCustomDetails;
import com.example.semiproject.dao.form.MemberEditForm;
import com.example.semiproject.dao.form.MemberJoinForm;
import com.example.semiproject.entity.Member;
import com.example.semiproject.repository.MemberRepository;
import com.example.semiproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class MemberController {
    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String gogo() {
        return "/account/login";
    }

    @GetMapping("register")
    public String join(Model model, @ModelAttribute MemberJoinForm joinForm) {
        model.addAttribute("form", joinForm);
        return "/account/register";
    }

    @PostMapping("register")
    public String save(@Validated @ModelAttribute(name = "form") MemberJoinForm joinForm,
                       BindingResult result,
                       Model model) {
       if(result.hasErrors()){
           model.addAttribute("form",joinForm);
           return "/account/register";
       }

        Member member = new Member();
        joinForm.setPassword(passwordEncoder.encode(joinForm.getPassword()));
        member.mappingJoinMember(joinForm);
        memberService.save(member);
        return "redirect:/login";
    }

    @GetMapping("edit")
    public String editPage(Model model, @AuthenticationPrincipal MemberCustomDetails user) {
        String name = user.getEmail();
        Member member = memberRepository.findByEmail(name).get();
        MemberEditForm form = new MemberEditForm();
        form.setEmail(member.getEmail());
        form.setPhone(member.getPhone());
        form.setName(member.getName());
        model.addAttribute("form", form);

        return "/account/edit";
    }

    @PostMapping("edit")
    public String edit(MemberEditForm form) {
        Member member = new Member();
        memberService.editMember(form);
        return "redirect:/post";
    }

    @ResponseBody
    @PostMapping("/pwck")
    public Boolean checkPassword(@RequestParam("password") String password,
                                 @AuthenticationPrincipal MemberCustomDetails memberCustomDetails) {
        if (encoder.matches(password, memberCustomDetails.getPassword())) {
            return true;
        } else {
            return false;
        }
    }


}
