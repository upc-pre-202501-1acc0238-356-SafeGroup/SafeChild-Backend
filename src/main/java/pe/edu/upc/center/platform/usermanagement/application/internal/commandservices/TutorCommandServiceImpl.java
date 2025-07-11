package pe.edu.upc.center.platform.usermanagement.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.usermanagement.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.CreateTutorByIdCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.CreateTutorCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.DeleteTutorCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.UpdateTutorCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.Address;
import pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.Document;
import pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.Email;
import pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.Phone;
import pe.edu.upc.center.platform.usermanagement.domain.services.TutorCommandService;
import pe.edu.upc.center.platform.usermanagement.infrastructure.persistence.jpa.repositories.TutorRepository;

import java.util.Optional;

@Service
public class TutorCommandServiceImpl implements TutorCommandService {

    private final TutorRepository tutorRepository;
    private final ExternalProfileService externalProfileService;


    public TutorCommandServiceImpl(TutorRepository tutorRepository, ExternalProfileService externalProfileService) {
        this.externalProfileService = externalProfileService;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public Long handle(CreateTutorCommand command) {
        var fullName = command.fullName();
        if (this.tutorRepository.existsByFullName(fullName)) {
            throw new IllegalArgumentException("Tutor with full name " + fullName + " already exists");
        }

        var profileId = externalProfileService.createProfile();
        if (profileId.isEmpty()) throw new IllegalArgumentException("Error while creating profile");

        var tutor = new Tutor(command);
        tutor.setProfileId(profileId.get());

        try {
            this.tutorRepository.save(tutor);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving Tutor: " + e.getMessage());
        }
        return tutor.getId();
    }

    @Override
    public Optional<Tutor> handle(CreateTutorByIdCommand command) {
        var tutorId = command.id();

        if (this.tutorRepository.existsById(tutorId)) {
            throw new IllegalArgumentException("Tutor with id " + tutorId + " already exists");
        }

        var profileId = externalProfileService.createProfile();
        if (profileId.isEmpty()) {
            throw new IllegalArgumentException("Error while creating profile");
        }

        var tutor = new Tutor();
        tutor.setId(tutorId);
        tutor.setFullName("Nuevo Tutor");
        tutor.setPassword("12345678");
        tutor.setMail(new Email("tutor" + tutorId + "@correo.com"));
        tutor.setDocument(new Document("00000000"));
        tutor.setPhone(new Phone("999999999"));
        tutor.setAddress(new Address("Dirección por defecto", "MIRAFLORES"));
        tutor.setRole("Tutor");
        tutor.setProfileId(profileId.get());

        try {
            return Optional.of(this.tutorRepository.save(tutor));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving Tutor: " + e.getMessage());
        }
    }


    @Override
    public Optional<Tutor> handle(UpdateTutorCommand command) {
        var tutorId = command.tutorId();
        var fullName = command.fullName();
        if (this.tutorRepository.existsByFullNameAndIdIsNot(fullName, tutorId)) {
            throw new IllegalArgumentException("Tutor with full name " + fullName + " already exists");
        }

        if (!this.tutorRepository.existsById(tutorId)) {
            throw new IllegalArgumentException("Tutor with id " + tutorId + " does not exist");
        }

        var tutorToUpdate = this.tutorRepository.findById(tutorId).get();
        tutorToUpdate.updateInformation(command.fullName(), command.email(), command.doc(), command.password(), command.number(), command.street(), command.district(), command.role());

        try {
            var updatedTutor = this.tutorRepository.save(tutorToUpdate);
            return Optional.of(updatedTutor);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating tutor: " + e.getMessage());
        }
    }


    @Override
    public void handle(DeleteTutorCommand command) {
        if (!this.tutorRepository.existsById(command.tutorId())) {
            throw new IllegalArgumentException("Tutor with id " + command.tutorId() + " does not exist");
        }

        // Try to delete the Tutor, if an error occurs, throw an exception
        try {
            this.tutorRepository.deleteById(command.tutorId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting Tutor: " + e.getMessage());
        }
    }
}
