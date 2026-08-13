package com.personalsandbox.workmanagement.api;

import com.personalsandbox.workmanagement.application.WorkHoursImportService;
import com.personalsandbox.workmanagement.domain.WorkHourRecord;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/work-hours")
public class WorkHoursController {

    private final WorkHoursImportService workHoursImportService;

    public WorkHoursController(WorkHoursImportService workHoursImportService) {
        this.workHoursImportService = workHoursImportService;
    }

    @GetMapping
    public List<WorkHourRecordResponse> list() {
        return workHoursImportService.listAll().stream().map(this::toResponse).toList();
    }

    @PostMapping(path = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public WorkHoursImportResponse importCsv(@RequestPart("file") MultipartFile file) {
        int count = workHoursImportService.importCsv(file);
        return new WorkHoursImportResponse(count, "Imported " + count + " work-hour record(s)");
    }

    private WorkHourRecordResponse toResponse(WorkHourRecord record) {
        return new WorkHourRecordResponse(
                record.getId(),
                record.getWorkDate(),
                record.getHours(),
                record.getLabel(),
                record.getNote());
    }
}
