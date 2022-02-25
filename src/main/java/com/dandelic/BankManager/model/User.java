package com.dandelic.BankManager.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    private String password;

    private String roles;

    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<BankAccount> bankAccountSet;

    protected User() {
    }

    public User(String username, String email, String password, String roles, boolean active, Set<BankAccount> bankAccountSet) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.active = active;
        this.bankAccountSet = bankAccountSet;
    }

    public User(String email, String password, Set<BankAccount> bankAccountSet) {
        this.email = email;
        this.password = password;
        this.bankAccountSet = bankAccountSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<BankAccount> getBankAccountSet() {
        return bankAccountSet;
    }

    public void setBankAccountSet(Set<BankAccount> bankAccountSet) {
        this.bankAccountSet = bankAccountSet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
