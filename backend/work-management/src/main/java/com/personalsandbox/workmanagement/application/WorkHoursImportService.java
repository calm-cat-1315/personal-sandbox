package com.personalsandbox.workmanagement.application;

import com.personalsandbox.workmanagement.domain.WorkHourRecord;
import com.personalsandbox.workmanagement.domain.WorkHourRecordRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkHoursImportService {

    private static final String HEADER_DATE = "work_date";
    private static final String HEADER_HOURS = "hours";
    private static final String HEADER_LABEL = "label";
    private static final String HEADER_NOTE = "note";

    private final WorkHourRecordRepository repository;

    public WorkHoursImportService(WorkHourRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public int importCsv(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidWorkHoursImportException("Import file is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase(Locale.ROOT).endsWith(".csv")) {
            throw new InvalidWorkHoursImportException("Import file must be a .csv file");
        }

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setIgnoreEmptyLines(true)
                        .setTrim(true)
                        .build()
                        .parse(reader)
        ) {
            Map<String, Integer> headerMap = parser.getHeaderMap();
            if (headerMap == null
                    || !headerMap.containsKey(HEADER_DATE)
                    || !headerMap.containsKey(HEADER_HOURS)) {
                throw new InvalidWorkHoursImportException(
                        "CSV must include headers: work_date, hours (optional: label, note)");
            }

            List<WorkHourRecord> records = new ArrayList<>();
            int rowNumber = 1;
            for (CSVRecord csvRecord : parser) {
                rowNumber++;
                if (isBlankRow(csvRecord)) {
                    continue;
                }
                records.add(toEntity(csvRecord, rowNumber));
            }

            if (records.isEmpty()) {
                throw new InvalidWorkHoursImportException("CSV contains no data rows");
            }

            repository.saveAll(records);
            return records.size();
        } catch (InvalidWorkHoursImportException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InvalidWorkHoursImportException("Failed to parse CSV import file", ex);
        }
    }

    @Transactional(readOnly = true)
    public List<WorkHourRecord> listAll() {
        return repository.findAllByOrderByWorkDateDescIdDesc();
    }

    private static boolean isBlankRow(CSVRecord csvRecord) {
        return csvRecord.stream().allMatch(value -> value == null || value.isBlank());
    }

    private static WorkHourRecord toEntity(CSVRecord csvRecord, int rowNumber) {
        String dateValue = csvRecord.get(HEADER_DATE);
        String hoursValue = csvRecord.get(HEADER_HOURS);

        if (dateValue == null || dateValue.isBlank() || hoursValue == null || hoursValue.isBlank()) {
            throw new InvalidWorkHoursImportException(
                    "Row " + rowNumber + " requires work_date and hours");
        }

        LocalDate workDate;
        try {
            workDate = LocalDate.parse(dateValue.trim());
        } catch (DateTimeParseException ex) {
            throw new InvalidWorkHoursImportException(
                    "Row " + rowNumber + " has invalid work_date (expected YYYY-MM-DD)", ex);
        }

        BigDecimal hours;
        try {
            hours = new BigDecimal(hoursValue.trim());
        } catch (NumberFormatException ex) {
            throw new InvalidWorkHoursImportException(
                    "Row " + rowNumber + " has invalid hours value", ex);
        }

        if (hours.signum() < 0) {
            throw new InvalidWorkHoursImportException("Row " + rowNumber + " hours must be >= 0");
        }

        String label = optional(csvRecord, HEADER_LABEL);
        String note = optional(csvRecord, HEADER_NOTE);
        return new WorkHourRecord(workDate, hours, label, note);
    }

    private static String optional(CSVRecord csvRecord, String header) {
        if (!csvRecord.isMapped(header)) {
            return null;
        }
        String value = csvRecord.get(header);
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
