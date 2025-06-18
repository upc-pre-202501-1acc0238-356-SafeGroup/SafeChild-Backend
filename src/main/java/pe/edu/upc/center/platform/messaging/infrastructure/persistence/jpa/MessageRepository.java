package pe.edu.upc.center.platform.messaging.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :userId AND m.receiver.id = :otherUserId) OR " +
            "(m.sender.id = :otherUserId AND m.receiver.id = :userId) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConversation(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    @Query("""
        SELECT m FROM Message m
        WHERE m.timestamp IN (
            SELECT MAX(m2.timestamp) FROM Message m2
            WHERE m2.sender.id = :userId OR m2.receiver.id = :userId
            GROUP BY 
              CASE 
                WHEN m2.sender.id = :userId THEN m2.receiver.id
                ELSE m2.sender.id
              END
        )
        ORDER BY m.timestamp DESC
        """)
    List<Message> findLastMessagesPerChat(@Param("userId") Long userId);

}