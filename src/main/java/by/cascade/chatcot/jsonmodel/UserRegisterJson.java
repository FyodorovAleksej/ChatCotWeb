package by.cascade.chatcot.jsonmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegisterJson {
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;
    @JsonProperty("repPassword")
    private String repPassword;
    @JsonProperty("email")
    private String email;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
