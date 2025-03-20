package com.harrisonwang.benefly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private LocalDateTime publishDate;
    private LocalDateTime expiryDate;
}
