package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginForm {
    private String email;
    private String password;

    @Builder
    public MemberLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
