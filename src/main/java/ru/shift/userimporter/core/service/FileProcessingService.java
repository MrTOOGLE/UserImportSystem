package ru.shift.userimporter.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.*;
import ru.shift.userimporter.core.repository.FileProcessingErrorRepository;
import ru.shift.userimporter.core.repository.UploadedFileRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FileProcessingService {
    private final UserService userService;
    private final FileProcessingErrorRepository errorRepository;
    private final UploadedFileRepository uploadedFileRepository;
    private final UserFileParser userFileParser;
    private final UserValidator userValidator;

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
                    User user = userFileParser.parseLineToUser(line);
                    userValidator.validateUser(user);
                    boolean userExists = userService.saveOrUpdateUser(user);

                    if (userExists) {
                        insertedCount++;
                    } else {
                        updatedCount++;
                    }

                } catch (Exception e) {
                    ErrorCode errorCode = userFileParser.determineErrorCode(e);
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
}
