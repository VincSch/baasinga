package com.vschwarzer.baasinga.web.form.application;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
public class ChangePasswordDTO {

    private String oldPw = "";
    private String newPw = "";
    private String confirmPw = "";

    public ChangePasswordDTO() {

    }

    public ChangePasswordDTO(String oldPw, String newPw, String confirmPw) {
        this.oldPw = oldPw;
        this.newPw = newPw;
        this.confirmPw = confirmPw;
    }

    public String getOldPw() {
        return oldPw;
    }

    public void setOldPw(String oldPw) {
        this.oldPw = oldPw;
    }

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }

    public String getConfirmPw() {
        return confirmPw;
    }

    public void setConfirmPw(String confirmPw) {
        this.confirmPw = confirmPw;
    }
}
