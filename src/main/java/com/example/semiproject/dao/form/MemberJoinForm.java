package com.example.semiproject.dao.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Data
public class MemberJoinForm {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Length(min=4, max=100, message = "4글자 이상 20글자 이하로 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "숫자와 영어만 입력하세요.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private String phone;
}
