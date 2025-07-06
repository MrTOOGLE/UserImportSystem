package ru.shift.userimporter.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.api.dto.DetailedFileStatistic;
import ru.shift.userimporter.api.dto.FileResponse;
import ru.shift.userimporter.api.dto.FileStatistic;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Long fileId = fileService.uploadFile(file);
        return ResponseEntity.status(201).body(Map.of("fileId", fileId.toString()));
    }

    @PostMapping("/{fileId}/processing")
    public ResponseEntity<Void> processFile(@PathVariable Long fileId) {
        fileService.startProcessing(fileId);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<FileResponse>> getStatistics(@RequestParam(required = false) Status status) {
        List<FileResponse> fileResponses = fileService.getFileStatistics(status);
        return ResponseEntity.ok().body(fileResponses);
    }

    @GetMapping("/{fileId}/statistics")
    public ResponseEntity<DetailedFileStatistic> getDetailedStatistics(@PathVariable Long fileId) {
        return ResponseEntity.ok().body(fileService.getDetailedStatistics(fileId));
    }
}
