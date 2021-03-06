package com.my.payment.db.entity;

import com.my.payment.db.Role;
import com.my.payment.db.Status;
import java.io.Serializable;
import java.util.Objects;

/**
 * User bean
 */
public class User implements Serializable {
    private int userID;
    private String login;
    private Role role;
    private String password;
    private String email;
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID && login.equals(user.login) && role == user.role && password.equals(user.password) && email.equals(user.email) && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, login, role, password, email, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", role=" + role.toString() +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User(String login, Role role, String password, String email, Status status) {
        userID=-1;
        this.login = login;
        this.role = role;
        this.password = password;
        this.email = email;
        this.status = status;
    }
    public User(int userID,String login, Role role, String password, String email, Status status) {
        this.userID=userID;
        this.login = login;
        this.role = role;
        this.password = password;
        this.email = email;
        this.status = status;
    }
    public User() {}
}
