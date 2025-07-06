package ru.shift.userimporter.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileStatistic {
    private Integer insertedLinesCount;
    private Integer updatedLinesCount;
    private Integer errorProcessedLinesCount;
}