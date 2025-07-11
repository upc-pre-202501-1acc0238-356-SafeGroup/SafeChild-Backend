package pe.edu.upc.center.platform.usermanagement.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.usermanagement.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.usermanagement.domain.model.entities.CaregiverSchedule;
import pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.CompleteName;
import pe.edu.upc.center.platform.usermanagement.domain.services.CaregiverCommandService;
import pe.edu.upc.center.platform.usermanagement.infrastructure.persistence.jpa.repositories.CaregiverRepository;
import pe.edu.upc.center.platform.usermanagement.infrastructure.persistence.jpa.repositories.CaregiverScheduleRepository;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.*;

import pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.Districts;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CaregiverCommandServiceImpl implements CaregiverCommandService {

    private final CaregiverRepository caregiverRepository;
    private final CaregiverScheduleRepository caregiverScheduleRepository;
    private final ExternalProfileService externalProfileService;

    public CaregiverCommandServiceImpl(CaregiverRepository caregiverRepository, CaregiverScheduleRepository caregiverScheduleRepository, ExternalProfileService externalProfileService) {
        this.caregiverRepository = caregiverRepository;
        this.caregiverScheduleRepository = caregiverScheduleRepository;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public Caregiver handle(CreateCaregiverCommand command) {
        var profileId = externalProfileService.createProfile();

        if (profileId.isEmpty()) throw new IllegalArgumentException("Error while creating profile");

        var caregiver = new Caregiver(command);
        caregiver.setProfileId(profileId.get());

        return this.caregiverRepository.save(caregiver);
    }

    @Override
    public Optional<Caregiver> handle(CreateCaregiverByIdCommand command) {
        var caregiverId = command.id();


        var profileId = externalProfileService.createProfile();

        if (profileId.isEmpty()) throw new IllegalArgumentException("Error while creating profile");


        var caregiver = new Caregiver();
        caregiver.setId(caregiverId);
        caregiver.setCompleteName(new CompleteName(""));
        caregiver.setAge(0);
        caregiver.setAddress("");
        caregiver.setCaregiverExperience(0);
        caregiver.setCompletedServices(0);
        caregiver.setBiography("");
        caregiver.setProfileImage(""); // o alguna imagen temporal
        caregiver.setFarePerHour((double) 0);
        caregiver.setDistrictsScope(Districts.CHORRILLOS);
        caregiver.setProfileId(profileId.get());

        return Optional.of(this.caregiverRepository.save(caregiver));
    }

    @Override
    public CaregiverSchedule handle(CreateCaregiverScheduleCommand command) {
        Caregiver caregiver = this.caregiverRepository.findById(command.caregiverId())
                .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

        var caregiverSchedule = new CaregiverSchedule(command, caregiver);

        return this.caregiverScheduleRepository.save(caregiverSchedule);
    }

    @Override
    public Optional<Caregiver> handle(UpdateCaregiverBiographyCommand command) {
        var optionalCaregiver = this.caregiverRepository.findById(command.caregiverId());
        if (optionalCaregiver.isEmpty()) {
            throw new IllegalArgumentException("Caregiver not found");
        }

        optionalCaregiver = optionalCaregiver.map(caregiver -> {
            caregiver.setBiography(command.biography());
            return caregiver;
        });

        this.caregiverRepository.save(optionalCaregiver.get());

        return optionalCaregiver;
    }

    @Override
    public Optional<Caregiver> handle(UpdateCaregiverPlaceFareCommand command) {
        var optionalCaregiver = caregiverRepository.findById(command.caregiverId());
        if (optionalCaregiver.isEmpty()) {
            throw new IllegalArgumentException("Caregiver not found");
        }
        optionalCaregiver = optionalCaregiver.map(caregiver -> {
            caregiver.setDistrictsScope(Districts.valueOf(command.districtsScope()));
            caregiver.setFarePerHour(command.farePerHour());
            return caregiver;
        });
        caregiverRepository.save(optionalCaregiver.get());
        return optionalCaregiver;
    }

    @Override
    public Optional<CaregiverSchedule> handle(UpdateCaregiverScheduleCommand command) {
        var optionalCaregiverSchedule = this.caregiverScheduleRepository.findById(command.caregiverScheduleId());
        if (optionalCaregiverSchedule.isEmpty()) {
            throw new IllegalArgumentException("Caregiver Schedule not found");
        }

        optionalCaregiverSchedule = optionalCaregiverSchedule.map(caregiverSchedule -> {
            caregiverSchedule.setEndHour(command.endHour());
            caregiverSchedule.setStartHour(command.startHour());
            caregiverSchedule.setWeekDay(command.weekDay());
            return caregiverSchedule;
        });

        this.caregiverScheduleRepository.save(optionalCaregiverSchedule.get());

        return optionalCaregiverSchedule;
    }

    @Override
    public void handle(DeleteCaregiverScheduleCommand command) {
        if (!this.caregiverScheduleRepository.existsById(command.caregiverScheduleId())) {
            throw new IllegalArgumentException("Caregiver Schedule with id " + command.caregiverScheduleId() + " does not exist");
        }

        try {
            this.caregiverScheduleRepository.deleteById(command.caregiverScheduleId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting caregiver schedule: " + e.getMessage());
        }
    }

    @Override
    public Optional<Caregiver> handle(UpdateCaregiverCommand command) {
        var optionalCaregiver = this.caregiverRepository.findById(command.caregiverId());
        if (optionalCaregiver.isEmpty()) {
            return Optional.empty();
        }

        var caregiver = optionalCaregiver.get();
        caregiver.updateInformation(
                command.completeName(),
                command.age(),
                command.address(),
                command.caregiverExperience(),
                command.completedServices(),
                command.biography(),
                command.profileImage(),
                command.farePerHour(),
                command.districtsScope(),
                command.profileId()
        );

        this.caregiverRepository.save(caregiver);
        return Optional.of(caregiver);
    }
}