package pe.edu.upc.center.platform.user.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.user.domain.model.queries.GetTutorByIdQuery;
import pe.edu.upc.center.platform.user.domain.services.TutorCommandService;
import pe.edu.upc.center.platform.user.domain.services.TutorQueryService;

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
}
