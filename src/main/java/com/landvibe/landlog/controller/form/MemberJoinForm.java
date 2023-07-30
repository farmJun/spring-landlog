package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinForm {
    private String name;
    private String email;
    private String password;

    @Builder
    public MemberJoinForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
