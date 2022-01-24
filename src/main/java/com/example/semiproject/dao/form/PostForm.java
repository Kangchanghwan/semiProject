package com.example.semiproject.dao.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostForm {

    private Long id;
    @NotBlank
    @Length(min = 1 ,max = 30)
    private String title;
    @Length(min= 1)
    @NotBlank
    private String content;
    private Boolean removeYn;
    private String email;

}
