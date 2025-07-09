package ru.shift.userimporter.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String phone;
    private String name;
    private String lastName;
    private String middleName;
    private String email;
    private LocalDate birthDate;
    private ZonedDateTime creationTime;
    private ZonedDateTime updateTime;
}
