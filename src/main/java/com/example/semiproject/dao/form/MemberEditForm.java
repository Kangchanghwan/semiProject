package com.example.semiproject.dao.form;

import com.example.semiproject.entity.Member;
import lombok.Data;

@Data
public class MemberEditForm {

    private String name;
    private String email;
    private String password;
    private String phone;


}
