package pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleShiftRepository extends JpaRepository<ScheduleShift, Long> {

    boolean existsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);

    List<ScheduleShift> findByScheduleIdAndAvailable(Long scheduleId, boolean available);



}
