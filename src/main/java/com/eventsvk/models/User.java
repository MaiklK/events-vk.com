package com.eventsvk.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"username"})
})
public class User {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "seq_user", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    private Long id;
    @Column(name = "username", nullable = false)
    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя пользователя должно быть от 2 до 30 символов")
    private String username;
    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Поле пароль не должно быть пустым")
    private String password;
    @Email
    @Column(name = "email", nullable = false)
    @NotEmpty(message = "Поле почта не должно быть пустым")
    private String email;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
