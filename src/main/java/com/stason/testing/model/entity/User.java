package com.stason.testing.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * It is a class-model from database table USER
 * @author Stanislav Hlova
 * @version 1.0
 */
public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String salt;
    private String name;
    private String surname;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public User() {
    }

    public User(int id, String login, String name, String surname, int idRole, boolean blocked) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.idRole = idRole;
        this.blocked = blocked;
    }

    public User(String login, String password, String salt, String name, String surname, int idRole, boolean blocked) {
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.surname = surname;
        this.idRole = idRole;
        this.blocked = blocked;
    }

    public User(int id, String login, String password, String salt, String name, String surname, int idRole, boolean blocked) {
        this(id,login,name,surname, idRole,blocked);
        this.password = password;
        this.salt = salt;
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

    private int idRole;
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
                ", role='" + idRole + '\'' +
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

    /**
     * Get role of the user
     * @return return a converted role from idRole to Enum.Role
     * @see Role
     */
    public String getRole() {
        for(Role role:Role.values()){
            if(role.getId()==this.idRole) return role.name();
        }
        return "GUEST";
    }
    /**
     * Set role for the user
     * @param role an Enum.Role constant
     * @see Role
     */
    public void setRole(Role role) {
        setIdRole(role.getId());
    }

    public boolean isBlocked() {
        return blocked;
    }
    public String getStringIntBlocked(){
        return blocked ? "1":"0";
    }
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
        return id == user.id && idRole == user.idRole && blocked == user.blocked && login.equals(user.login) && Objects.equals(password, user.password) && Objects.equals(salt, user.salt) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(idPassedTestList, user.idPassedTestList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, salt, name, surname, idRole, blocked, idPassedTestList);
    }
}
