package com.dandelic.BankManager.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @ToString.Include
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    private String password;

    private String roles;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<BankAccount> bankAccountSet;


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


    public User(String username, String email, String password, String roles, boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.active = active;
    }
}
