package pe.edu.upc.center.platform.user.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Tutor;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    boolean existsByFullName(String fullName);
    boolean existsByFullNameAndIdIsNot(String fullName, Long id);
    Optional<Tutor> findByFullName(String fullName);
}
