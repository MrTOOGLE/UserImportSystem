package ru.shift.userimporter.core.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.*;
import ru.shift.userimporter.core.repository.FileProcessingErrorRepository;
import ru.shift.userimporter.core.repository.UploadedFileRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class FileProcessingService {
    private final UserService userService;
    private final FileProcessingErrorRepository errorRepository;
    private final UploadedFileRepository uploadedFileRepository;

    public FileProcessingService(UserService userService,
                                 FileProcessingErrorRepository errorRepository, UploadedFileRepository uploadedFileRepository) {
        this.userService = userService;
        this.errorRepository = errorRepository;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Async
    public void processFile(UploadedFile uploadedFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(uploadedFile.getStoragePath()), StandardCharsets.UTF_8))) {
            String line;
            int rowNumber = 0;
            int insertedCount = 0;
            int updatedCount = 0;

            while ((line = bufferedReader.readLine()) != null) {
                rowNumber++;
                try {
                    User user = parseLineToUser(line);
                    boolean userExsits = userService.existsByPhone(user.getPhone());

                    userService.saveOrUpdateUser(user);
                    if (userExsits) {
                        updatedCount++;
                    } else {
                        insertedCount++;
                    }

                } catch (Exception e) {
                    ErrorCode errorCode = determineErrorCode(e);
                    saveProcessingError(uploadedFile, rowNumber, line, errorCode, e.getMessage());
                }
            }
            uploadedFile.setStatus(Status.DONE);
            uploadedFile.setInsertedRows(insertedCount);
            uploadedFile.setUpdatedRows(updatedCount);
            uploadedFileRepository.save(uploadedFile);

        } catch (Exception e) {
            uploadedFile.setStatus(Status.FAILED);
            uploadedFileRepository.save(uploadedFile);
        }
    }

    private void saveProcessingError(UploadedFile uploadedFile, int rowNumber, String rawData, ErrorCode errorCode, String errorMessage) {
        FileProcessingError error = new FileProcessingError();
        error.setUploadedFile(uploadedFile);
        error.setRowNumber(rowNumber);
        error.setRawData(rawData);
        error.setErrorCode(errorCode);
        error.setErrorMessage(errorMessage);
        errorRepository.save(error);
    }

    private User parseLineToUser(String line) {
        String[] fields = line.split(",");

        if (fields.length != 6) {  // ДОБАВЬ ПРОВЕРКУ!
            throw new IllegalArgumentException("Неверный формат: ожидалось 6 полей");
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

    private ErrorCode determineErrorCode(Exception e) {
        if (e instanceof DateTimeParseException) {
            return ErrorCode.INVALID_BIRTHDATE;
        }
        if (e.getMessage().contains("email")) {
            return ErrorCode.INVALID_EMAIL;
        }
        if (e.getMessage().contains("phone")) {
            return ErrorCode.INVALID_PHONE;
        }
        if (e.getMessage().contains("firstName")) {
            return ErrorCode.INVALID_NAME;
        }
        if (e.getMessage().contains("lastName")) {
            return ErrorCode.INVALID_LAST_NAME;
        }
        if (e.getMessage().contains("middleName")) {
            return ErrorCode.INVALID_MIDDLE_NAME;
        }
        return ErrorCode.INVALID_FORMAT;
    }
}
