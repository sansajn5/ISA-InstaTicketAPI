package com.isa.instaticketapi.service.dto.account;

public class ChangedRoleDTO {

    private String password;
    private String repassword;
    private String username;

    public ChangedRoleDTO() {

    }

    public ChangedRoleDTO(String password, String repassword, String username) {
        this.password = password;
        this.repassword = repassword;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
