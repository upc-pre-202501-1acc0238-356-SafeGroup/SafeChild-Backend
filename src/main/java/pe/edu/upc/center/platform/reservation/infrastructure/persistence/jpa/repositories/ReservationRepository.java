package pe.edu.upc.center.platform.reservation.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCaregiverId(Long caregiverId);
    List<Reservation> findByTutorId(Long tutorId);
}