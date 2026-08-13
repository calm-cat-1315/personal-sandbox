package com.personalsandbox.workmanagement.api;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WorkHourRecordResponse(
        Long id,
        LocalDate workDate,
        BigDecimal hours,
        String label,
        String note
) {
}
