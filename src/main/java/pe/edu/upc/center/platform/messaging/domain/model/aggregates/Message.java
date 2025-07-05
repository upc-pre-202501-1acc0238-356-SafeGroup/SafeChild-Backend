package pe.edu.upc.center.platform.messaging.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Tutor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor sender;

    @ManyToOne
    @JoinColumn(name = "caregiver_id")
    private Caregiver receiver;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Message() {}

    public Message(Tutor sender, Caregiver receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}
