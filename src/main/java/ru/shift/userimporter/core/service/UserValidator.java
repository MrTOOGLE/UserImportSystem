package ru.shift.userimporter.core.service;

import org.springframework.stereotype.Component;
import ru.shift.userimporter.api.error.BusinessException;
import ru.shift.userimporter.api.error.ErrorType;
import ru.shift.userimporter.core.model.User;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[А-ЯЁ][а-яёА-ЯЁ'\\- ]{2,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%-]+@(shift\\.com|shift\\.ru)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^7[0-9]{10}$");

    public void validateUser(User user) {
        validateName(user.getFirstName(), "Имя");
        validateName(user.getLastName(), "Фамилия");

        if (user.getMiddleName() != null && !user.getMiddleName().trim().isEmpty()) {
            validateName(user.getMiddleName(), "Отчество");
        }

        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        validateBirthDate(user.getBirthDate());
    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, fieldName + " обязательно");
        }

        name = name.trim();
        if (name.length() < 3 || name.length() > 50) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, fieldName + " должно быть от 3 до 50 символов");
        }

        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, fieldName + " должно начинаться с заглавной буквы");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Email обязателен");
        }

        if (email.length() > 100) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Email слишком длинный");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Email должен быть в домене shift.com или shift.ru");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Телефон обязателен");
        }

        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Телефон должен начинаться с 7 и содержать 11 цифр");
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Дата рождения обязательна");
        }

        if (birthDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Возраст должен быть не менее 18 лет");
        }
    }
}