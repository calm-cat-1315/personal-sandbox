package com.personalsandbox.workmanagement.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkHourRecordRepository extends JpaRepository<WorkHourRecord, Long> {

    List<WorkHourRecord> findAllByOrderByWorkDateDescIdDesc();
}
