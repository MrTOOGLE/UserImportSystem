package ru.shift.userimporter.core.service;

import org.springframework.stereotype.Component;
import ru.shift.userimporter.api.error.BusinessException;
import ru.shift.userimporter.api.error.ErrorType;
import ru.shift.userimporter.core.model.ErrorCode;
import ru.shift.userimporter.core.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class UserFileParser {
    public User parseLineToUser(String line) {
        String[] fields = line.split(",");

        if (fields.length != 6) {
            throw new BusinessException(ErrorType.VALIDATION_ERROR, "Неверный формат: ожидалось 6 полей");
        }
        User user = new User();

        user.setFirstName(fields[0].trim());
        user.setLastName(fields[1].trim());
        user.setMiddleName(fields[2].trim().isEmpty() ? null : fields[2].trim());
        user.setEmail(fields[3].trim());
        user.setPhone(fields[4].trim());
        user.setBirthDate(LocalDate.parse(fields[5].trim(), DateTimeFormatter.ISO_LOCAL_DATE));

        return user;
    }

    public ErrorCode determineErrorCode(Exception e) {
        String message = e.getMessage().toLowerCase();

        if (message.contains("дата") || message.contains("birthdate") || message.contains("возраст")) {
            return ErrorCode.INVALID_BIRTHDATE;
        }
        if (message.contains("email") || message.contains("почта")) {
            return ErrorCode.INVALID_EMAIL;
        }
        if (message.contains("телефон") || message.contains("phone")) {
            return ErrorCode.INVALID_PHONE;
        }
        if (message.contains("имя") || message.contains("firstname")) {
            return ErrorCode.INVALID_NAME;
        }
        if (message.contains("фамилия") || message.contains("lastname")) {
            return ErrorCode.INVALID_LAST_NAME;
        }
        if (message.contains("отчество") || message.contains("middlename")) {
            return ErrorCode.INVALID_MIDDLE_NAME;
        }

        return ErrorCode.INVALID_FORMAT;
    }
}
