package pe.edu.upc.center.platform.user.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.domain.model.entities.CaregiverSchedule;

import java.util.List;

@Repository
public interface CaregiverScheduleRepository extends JpaRepository<CaregiverSchedule, Long> {
    List<CaregiverSchedule> findByCaregiver(Caregiver caregiver);

}
