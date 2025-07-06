package ru.shift.userimporter.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shift.userimporter.core.model.FileProcessingError;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailedFileStatistic {
    private Integer insertedLinesCount;
    private Integer updatedLinesCount;
    private List<FileProcessingError> errors;
}
