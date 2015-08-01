package com.vschwarzer.baasinga.domain.dto.common;

/**
 * Created by Vincent Schwarzer on 01.08.15.
 */
public class ChangeProfileDTO {
    private String email = "";
    private String firstname = "";
    private String lastname = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
