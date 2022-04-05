package com.stason.testing.model.entity;

import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private String salt;
    private String name;
    private String surname;

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public User(String login, String password, String salt, String name, String surname) {
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.surname = surname;
    }

    private int id_role;
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
                ", role='" + id_role + '\'' +
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
        for(Role role:Role.values()){
            if(role.getId()==this.id_role) return role.name();
        }
        return "GUEST";
    }

    public void setRole(Role role) {
        setId_role(role.getId());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && id_role == user.id_role && blocked == user.blocked && login.equals(user.login) && Objects.equals(password, user.password) && Objects.equals(salt, user.salt) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(idPassedTestList, user.idPassedTestList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, salt, name, surname, id_role, blocked, idPassedTestList);
    }
}
