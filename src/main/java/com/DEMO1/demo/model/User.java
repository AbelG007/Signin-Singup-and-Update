package com.DEMO1.demo.model;

import com.DEMO1.demo.common.enumeration.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Setter
    @Getter
    @Column(nullable = false, unique = false, length = 64)
    private String password;

    @Setter
    @Getter
    @Column(nullable = false, unique = false, length = 45)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;

    @Setter
    @Getter
    @Column(nullable = false, unique = false, length = 45)
    private String email;

    @Setter
    @Getter
    @Column(nullable = false, unique = false, length = 12)
    private Long number;

    @Setter
    @Getter
    @Column(nullable = false, unique = false, length = 250)
    private String address;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 10) // Adjust length based on enum values
    private Gender gender;

    @Setter
    @Getter
    @Column(nullable = false)
    private LocalDate dob;


}
