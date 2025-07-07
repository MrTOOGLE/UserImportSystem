package ru.shift.userimporter.api.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shift.userimporter.api.dto.ClientResponse;
import ru.shift.userimporter.api.mapper.ClientMapper;
import ru.shift.userimporter.core.model.User;
import ru.shift.userimporter.core.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final UserService userService;
    private final ClientMapper clientMapper;

    @GetMapping
    public List<ClientResponse> getClients(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam @Min(1) int limit,
            @RequestParam @Min(0) int offset)
    {
        if (offset % limit != 0) {
            throw new IllegalArgumentException("Offset должен быть кратен limit и оба должны быть больше нуля");
        }
        List<User> users = userService.getUsers(phone, name, lastName, email, limit, offset).getContent();
        List<ClientResponse> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(clientMapper.userToClientResponse(user));
        }
        return responses;
    }
}
