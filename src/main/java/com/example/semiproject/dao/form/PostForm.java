package com.example.semiproject.dao.form;

import lombok.Data;

@Data
public class PostForm {

    private Long id;
    private String title;
    private String content;
    private Boolean removeYn;
    private String email;

}
