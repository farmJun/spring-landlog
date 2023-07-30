package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberLoginForm {
    private String email;
    private String password;

    public MemberLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
