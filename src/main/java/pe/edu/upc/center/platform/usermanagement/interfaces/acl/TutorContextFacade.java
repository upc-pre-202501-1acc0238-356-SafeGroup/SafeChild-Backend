package pe.edu.upc.center.platform.usermanagement.interfaces.acl;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.center.platform.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.center.platform.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.CreateTutorByIdCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.CreateTutorCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.queries.GetTutorByIdQuery;
import pe.edu.upc.center.platform.usermanagement.domain.services.TutorCommandService;
import pe.edu.upc.center.platform.usermanagement.domain.services.TutorQueryService;

import java.util.Optional;

@Service
public class TutorContextFacade {

    private final TutorCommandService tutorCommandService;
    private final TutorQueryService tutorQueryService;

    public TutorContextFacade(TutorCommandService tutorCommandService, TutorQueryService tutorQueryService) {
        this.tutorCommandService = tutorCommandService;
        this.tutorQueryService = tutorQueryService;
    }

    public Optional<Tutor> fetchTutorById(Long tutorId) {
        var GetTutorByIdQuery = new GetTutorByIdQuery(tutorId);
        return tutorQueryService.handle(GetTutorByIdQuery);
    }

    public Optional<Tutor> createTutorById(Long tutorId){
        var createTutorCommand = new CreateTutorByIdCommand(tutorId);
        return tutorCommandService.handle(createTutorCommand);
    }

}
