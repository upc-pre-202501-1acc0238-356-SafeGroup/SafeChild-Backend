package pe.edu.upc.center.platform.user.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.user.domain.model.commands.CreateCaregiverCommand;
import pe.edu.upc.center.platform.user.domain.model.commands.CreateTutorCommand;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.CompleteName;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.Email;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "complete_name",nullable = false)
    @Embedded
    private CompleteName completeName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="email",column = @Column(name = "email", length = 50, nullable = false))
    })
    private Email mail;


    public void updateEmail(String email) {
        this.mail = new Email(email);
    }

    public User() {}

    public User(Long id, CompleteName completeName, Email mail) {
        this.id = id;
        this.completeName = completeName;
        this.mail = mail;
    }

    public User(CreateCaregiverCommand command) {
        this.completeName = new CompleteName(command.completeName());
        this.mail = new Email(command.email());
    }

    public User(CreateTutorCommand command) {
        this.completeName = new CompleteName(command.fullName());
        this.mail = new Email(command.email());
    }


    public Long getId() {
        return id;
    }

    public CompleteName getCompleteName() {
        return completeName;
    }

    public Email getMail() {
        return mail;
    }
}
