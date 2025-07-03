package ru.shift.userimporter.core.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(nullable = false, length = 50, name = "first_name")
    @Size(max = 50)
    private String firstName;

    @Column(nullable = false, length = 50, name = "last_name")
    @Size(max = 50)
    private String lastName;

    @Column(length = 50, name = "middle_name")
    @Size(max = 50)
    private String middleName;

    @Column(nullable = false, length = 100)
    @Email
    @Size(max = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    @Size(min = 11, max = 11)
    private String phone;
}
