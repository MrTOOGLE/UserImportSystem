package ru.shift.userimporter.core.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.shift.userimporter.core.model.User;

/**
 * Класс для создания JPA Specifications для поиска пользователей.
 * Используется для построения динамических WHERE условий.
 */
public class UserSpecifications {
    public static Specification<User> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            if (phone == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("phone"), phone);
        };
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("email"), email);
        };
    }

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("firstName"), firstName);
        };
    }

    public static Specification<User> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("lastName"), lastName);
        };
    }
}
