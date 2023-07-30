package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberJoinForm {
    private String name;
    private String email;
    private String password;

    public MemberJoinForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
