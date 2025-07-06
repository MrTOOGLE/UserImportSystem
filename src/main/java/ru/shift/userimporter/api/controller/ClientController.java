package ru.shift.userimporter.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shift.userimporter.api.dto.ClientResponse;
import ru.shift.userimporter.core.model.User;
import ru.shift.userimporter.core.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final UserService userService;

    public ClientController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<ClientResponse>> getClients(
            @RequestParam(required = false) Long phone,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "0") int offset)
    {
        List<User> users = userService.getUsers(phone != null ? phone.toString() : null, name, lastName, email, limit, offset).getContent();
        List<ClientResponse> responses = new ArrayList<>();
        for (User user : users) {
            ClientResponse clientResponse = new ClientResponse();
            clientResponse.setPhone(Long.valueOf(user.getPhone()));
            clientResponse.setName(user.getFirstName());
            clientResponse.setLastName(user.getLastName());
            clientResponse.setMiddleName(user.getMiddleName());
            clientResponse.setEmail(user.getEmail());
            clientResponse.setBirthDate(user.getBirthDate().toString());
            clientResponse.setCreationTime(user.getCreatedAt().toString());
            clientResponse.setUpdateTime(user.getUpdatedAt().toString());
            responses.add(clientResponse);
        }
        return ResponseEntity.ok(responses);

    }
}
