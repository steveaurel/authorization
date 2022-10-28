package com.spid.aruba.authorization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NOME", nullable = false, length = 100)
    private String name;
    @Column(name = "COGNOME", nullable = false)
    private String surName;
    @Column(name = "CODICE_FISCALE", unique = true, nullable = false)
    private String codiceFiscale;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDateTime createTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private AppRole role;

    @Transient
    private String accessToken;
    @Transient
    private String refreshToken;

    public AppUser() {

    }

    public AppUser(Long id, String name, String surName, String codiceFiscale, String email, String password) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.password = password;
    }
}
