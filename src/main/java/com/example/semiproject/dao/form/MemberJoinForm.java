package com.example.semiproject.dao.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Data
public class MemberJoinForm {
    @NotBlank(message = "필수 입력 값 입니다.")
    private String name;
    @NotBlank
    @Email(message = "형식이 틀립니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Length(min=4, max=100, message = "4글자 이상 20글자 이하로 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "숫자와 영어만 입력하세요.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^[0-9]+$" ,message = "숫자만 입력하세요.")
    private String phone;
}
