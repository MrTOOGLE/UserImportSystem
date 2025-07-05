package ru.shift.userimporter.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.model.UploadedFile;
import ru.shift.userimporter.core.repository.UploadedFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileService {
    private final UploadedFileRepository uploadedFileRepository;
    private final FileProcessingService fileProcessingService;

    @Value("${app.upload-path}")
    private String uploadPath;

    public FileService(UploadedFileRepository uploadedFileRepository, FileProcessingService fileProcessingService) {
        this.uploadedFileRepository = uploadedFileRepository;
        this.fileProcessingService = fileProcessingService;
    }

    public Long uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Пустой файл");
        }

        String hash = calculateHash(file);

        if (uploadedFileRepository.findByHash(hash).isPresent()) {
            throw new IllegalArgumentException("Файл уже есть");
        }

        Files.createDirectories(Path.of(uploadPath));
        // Для исключения ошибки, когда файлы ↓ называются одинаково
        String filePath = uploadPath + "/" + UUID.randomUUID() + file.getOriginalFilename();
        try {
            Files.write(Path.of(filePath), file.getBytes());
        } catch (IOException e) {
            throw new IOException("Ошибка при сохранении файла: " + e.getMessage(), e);
        }

        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setOriginalFilename(file.getOriginalFilename());
        uploadedFile.setStoragePath(filePath);
        uploadedFile.setHash(hash);
        uploadedFile.setStatus(Status.NEW);

        UploadedFile saved = uploadedFileRepository.save(uploadedFile);
        return saved.getId();
    }

    public void startProcessing(Long fileId) {
        UploadedFile uploadedFile = uploadedFileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("Файл не найден"));
        if (uploadedFile.getStatus() != Status.NEW) {
            throw new IllegalArgumentException("Файл не новый, уже обрабатывался/обрабатывается");
        }

        uploadedFile.setStatus(Status.IN_PROGRESS);
        uploadedFileRepository.save(uploadedFile);

        fileProcessingService.processFile(uploadedFile);
    }

    private String calculateHash(MultipartFile file) {
        try {
            return DigestUtils.sha256Hex(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Проблема с файлом: " + e.getMessage(), e);
        }
    }


}
