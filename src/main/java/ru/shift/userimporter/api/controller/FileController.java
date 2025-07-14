package ru.shift.userimporter.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.core.model.DetailedFileStatistic;
import ru.shift.userimporter.api.dto.FileResponse;
import ru.shift.userimporter.api.dto.FileUploadResponse;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.service.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Long fileId = fileService.uploadFile(file);
        return new FileUploadResponse(fileId.toString());
    }

    @PostMapping("/{fileId}/processing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processFile(@PathVariable Long fileId) {
        fileService.startProcessing(fileId);
    }

    @GetMapping("/statistics")
    public List<FileResponse> getStatistics(@RequestParam(required = false) Status status) {
        return fileService.getFileStatistics(status);
    }

    @GetMapping("/{fileId}/statistics")
    public DetailedFileStatistic getDetailedStatistics(@PathVariable Long fileId) {
        return fileService.getDetailedStatistics(fileId);
    }
}
