package com.vaishnavi.servicebook.dto;

import lombok.Data;
import java.time.LocalTime;
import java.util.List;

@Data
public class WorkingScheduleDto {
    private List<Integer> offDays; // 1=Mon, 7=Sun
    private LocalTime startTime;
    private LocalTime endTime;
}
