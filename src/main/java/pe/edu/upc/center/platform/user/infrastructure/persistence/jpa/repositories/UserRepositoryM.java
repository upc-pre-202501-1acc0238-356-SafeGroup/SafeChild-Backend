package pe.edu.upc.center.platform.user.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.platform.user.domain.model.aggregates.User;


public interface UserRepositoryM extends JpaRepository<User, Long> {
}
