package pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories;


import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>  {

    Optional<Schedule> findById(Long id);

    List<Schedule> findScheduleByCaregiverId(Long caregiverId);

}
