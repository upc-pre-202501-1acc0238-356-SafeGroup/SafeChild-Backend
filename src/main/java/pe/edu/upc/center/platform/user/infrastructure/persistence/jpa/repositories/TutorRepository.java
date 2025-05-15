package pe.edu.upc.center.platform.user.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.user.domain.model.entities.Tutor;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.CompleteName;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    boolean existsByCompleteName(CompleteName completeName);
    boolean existsByCompleteNameAndIdIsNot(CompleteName completeName, Long id);
    Optional<Tutor> findByCompleteName(CompleteName completeName);
}
