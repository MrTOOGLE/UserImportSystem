package ru.shift.userimporter.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shift.userimporter.core.model.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private String fileId;
    private Status status;
    private FileStatistic statistic;
    private int hashCode;
}