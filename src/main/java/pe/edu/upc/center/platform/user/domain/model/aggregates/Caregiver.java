package pe.edu.upc.center.platform.user.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.user.domain.model.commands.CreateCaregiverCommand;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.CompleteName;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


@Entity
@Table(name = "Caregiver")
public class Caregiver extends AuditableAbstractAggregateRoot<Caregiver> {

    @Getter
    @Column(name = "complete_name",nullable = false)
    @Embedded
    private CompleteName completeName;

    @Getter
    @Column(name = "age",nullable = false)
    private Integer age;

    @Getter
    @Column(name = "address",nullable = false)
    private String address;

    @Getter
    @Column(name = "caregiver_experience",nullable = false)
    private Integer caregiverExperience;

    @Getter
    @Column(name = "completed_services",nullable = false)
    private Integer completedServices;

    @Getter
    @Setter
    @Column(name = "biography", columnDefinition = "TEXT" , nullable = false)
    private String biography;

    @Getter
    @Column(name = "profile_image",nullable = false)
    private String profileImage;

    @Getter
    @Setter
    @Column(name = "fare_per_hour",nullable = false)
    private Double farePerHour;

    @Getter
    @Setter
    @Column(name = "district_scope",nullable = false)
    private String districtsScope;

    @Getter
    @Setter
    @Column(name = "profile_id")
    private Long profileId;



//    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Reservation> reservations = new ArrayList<>();

    public Caregiver(CreateCaregiverCommand command) {
        this.completeName = new CompleteName(command.completeName());
        this.age = command.age();
        this.address = command.address();
        this.caregiverExperience = command.caregiverExperience();
        this.completedServices = command.completedServices();
        this.biography = command.biography();
        this.profileImage = command.profileImage();
        this.farePerHour = command.farePerHour();
        this.districtsScope = command.districtsScope();
    }

    public Caregiver() {

    }
}
