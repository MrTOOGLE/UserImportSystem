package ru.shift.userimporter.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private Long phone;
    private String name;
    private String lastName;
    private String middleName;
    private String email;
    private String birthDate;
    private String creationTime;
    private String updateTime;
}
