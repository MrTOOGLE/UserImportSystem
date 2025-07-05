package ru.shift.userimporter.core.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.UploadedFile;
import ru.shift.userimporter.core.repository.FileProcessingErrorRepository;

@Service
public class FileProcessingService {
    private final UserService userService;
    private final FileProcessingErrorRepository errorRepository;

    public FileProcessingService(UserService userService,
                                 FileProcessingErrorRepository errorRepository) {
        this.userService = userService;
        this.errorRepository = errorRepository;
    }

    @Async
    public void processFile(UploadedFile uploadedFile) {

    }
}
