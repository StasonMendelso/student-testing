package com.stason.testing.model.entity;

import java.util.List;

public class User {
    private int id;
    private String login;
    private String password;
    private String salt;
    private String name;
    private String surname;
    private Role role;
    private boolean blocked;
    private List<Integer> idPassedTestList;
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role='" + role + '\'' +
                ", blocked=" + blocked +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role.name();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }
    public String getStringIntBlocked(){
        return blocked ? "1":"0";
    }
    public String getStringBlocked(){return blocked? "Так":"Ні";}
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public List<Integer> getIdPassedTestList() {
        return idPassedTestList;
    }

    public void setIdPassedTestList(List<Integer> idPassedTestList) {
        this.idPassedTestList = idPassedTestList;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
