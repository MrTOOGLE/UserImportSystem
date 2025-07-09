package ru.shift.userimporter.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.User;
import ru.shift.userimporter.core.repository.UserRepository;
import ru.shift.userimporter.core.repository.UserSpecifications;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getUsers(String phone, String firstName, String lastName, String email, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Specification<User> specification = UserSpecifications.hasPhone(phone)
                .and(UserSpecifications.hasFirstName(firstName))
                .and(UserSpecifications.hasLastName(lastName))
                .and(UserSpecifications.hasEmail(email));

        return userRepository.findAll(specification, pageable);
    }

    public boolean saveOrUpdateUser(User user) {
        boolean existed = userRepository.findByPhone(user.getPhone()).isPresent();

        if (existed) {
            user.setId(userRepository.findByPhone(user.getPhone()).get().getId());
        }
        userRepository.save(user);
        return !existed;
    }

    public boolean existsByPhone(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }
}
