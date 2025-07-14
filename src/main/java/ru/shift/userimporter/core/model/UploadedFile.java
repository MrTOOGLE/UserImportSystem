package ru.shift.userimporter.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "uploaded_files")
@Getter
@Setter
@NoArgsConstructor
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "original_filename", length = 50, nullable = false)
    @Size(max = 50)
    private String originalFilename;

    @Column(name = "storage_path", length = 512, nullable = false)
    @Size(max = 512)
    private String storagePath;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Status status = Status.NEW;

    @Column(length = 40, nullable = false)
    @Size(max = 40)
    private String hash;

    @Column(name = "inserted_rows")
    private Integer insertedRows;

    @Column(name = "updated_rows")
    private Integer updatedRows;
}
