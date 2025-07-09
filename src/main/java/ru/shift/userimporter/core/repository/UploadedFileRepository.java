package ru.shift.userimporter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.model.UploadedFile;

import java.util.List;
import java.util.Optional;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
    @Query("SELECT u FROM UploadedFile u WHERE (:status IS NULL OR u.status = :status)")
    List<UploadedFile> findByStatus(@Param("status") Status status);

    Optional<UploadedFile> findByHash(String hash);
}
