package ru.shift.userimporter.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_processing_errors")
@Getter
@Setter
@NoArgsConstructor
public class FileProcessingError {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    @JsonIgnore // TODO - убрать!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private UploadedFile uploadedFile;

    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @Column(name = "error_code", nullable = false, length = 30)
    @Size(max = 30)
    @Enumerated(EnumType.STRING)
    private ErrorCode errorCode;

    @Column(name = "raw_data")
    private String rawData;
}
