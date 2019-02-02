package me.matryoshkadoll.app.api.model;

public class UserInfo {
    private String email;
    private String password;

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}
