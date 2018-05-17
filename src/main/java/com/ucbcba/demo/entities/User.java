package com.ucbcba.demo.entities;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
//@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min=1, message="This field cannot be blank")
    private String username;
    @Size(min=1, message="This field cannot be blank")
    private String password;
    @Size(min=1, message="You must confirm your password")
    private String passwordConfirm;
    private String role = "user";
    @Size(min=1, message="This field cannot be blank")
    private String email;
    @Size(min=1, message="This field cannot be blank")
    private String firstName;
    @Size(min=1, message="This field cannot be blank")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
                                        