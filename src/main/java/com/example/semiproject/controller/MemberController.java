package com.example.semiproject.controller;

import com.example.semiproject.dao.MemberCustomDetails;
import com.example.semiproject.dao.form.MemberEditForm;
import com.example.semiproject.dao.form.MemberJoinForm;
import com.example.semiproject.entity.Member;
import com.example.semiproject.repository.MemberCustomRepository;
import com.example.semiproject.repository.MemberRepository;
import com.example.semiproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class MemberController {
    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberCustomRepository memberCustomRepository;

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
        if (result.hasErrors()) {
            model.addAttribute("form", joinForm);
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
        memberService.editMember(form);
        return "redirect:/post";
    }

    @ResponseBody
    @PostMapping("/pwck")
    public Boolean checkPassword(@RequestParam("password") String password,
                                 @AuthenticationPrincipal MemberCustomDetails memberCustomDetails) {
        return encoder.matches(password, memberCustomDetails.getPassword());
    }

    @GetMapping("/users")
    public String userList(@AuthenticationPrincipal MemberCustomDetails memberCustomDetails,
                           Model model,
                           @RequestParam(required = false, defaultValue = "") String email,
                           @RequestParam(required = false, defaultValue = "ROLE_USER") String role) {
        boolean role_admin = memberCustomDetails.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        if (role_admin) {
            List<Member> members = memberCustomRepository.findByEmailContainAndRole(email, role);
            model.addAttribute("list", members);
            return "/admin/userList";
        } else {
            return "redirect:/post";
        }
    }

    @ResponseBody
    @PostMapping("/redundancy")
    public Boolean checkEmail(String email) {
        return memberRepository.findByEmail(email).isEmpty()&&isValidEmail(email);
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

}
