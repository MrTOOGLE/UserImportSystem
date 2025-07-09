package ru.shift.userimporter.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.api.dto.DetailedFileStatistic;
import ru.shift.userimporter.api.dto.FileResponse;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Long fileId = fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("fileId", fileId.toString()));
    }

    @PostMapping("/{fileId}/processing")
    public ResponseEntity<Void> processFile(@PathVariable Long fileId) {
        fileService.startProcessing(fileId);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
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
