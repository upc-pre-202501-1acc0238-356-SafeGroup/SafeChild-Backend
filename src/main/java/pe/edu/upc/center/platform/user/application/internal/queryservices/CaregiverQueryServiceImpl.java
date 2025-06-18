package pe.edu.upc.center.platform.user.application.internal.queryservices;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.domain.model.entities.CaregiverSchedule;
import pe.edu.upc.center.platform.user.domain.model.queries.GetAllCaregiverQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetAllCaregiverScheduleByCaregiverIdQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetCaregiverByIdQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetCaregiverByLocationQuery;
import pe.edu.upc.center.platform.user.domain.services.CaregiverQueryService;
import pe.edu.upc.center.platform.user.infrastructure.persistence.jpa.repositories.CaregiverRepository;
import pe.edu.upc.center.platform.user.infrastructure.persistence.jpa.repositories.CaregiverScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CaregiverQueryServiceImpl implements CaregiverQueryService {

    private final CaregiverRepository caregiverRepository;
    private final CaregiverScheduleRepository caregiverScheduleRepository;

    public CaregiverQueryServiceImpl(CaregiverRepository caregiverRepository, CaregiverScheduleRepository caregiverScheduleRepository) {
        this.caregiverRepository = caregiverRepository;
        this.caregiverScheduleRepository = caregiverScheduleRepository;
    }

    @Override
    public List<Caregiver> handle(GetAllCaregiverQuery query) {
        return caregiverRepository.findAll();
    }

    @Override
    public List<CaregiverSchedule> handle(GetAllCaregiverScheduleByCaregiverIdQuery query) {

        Caregiver caregiver = this.caregiverRepository.findById(query.caregiverId())
                .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

        return caregiverScheduleRepository.findByCaregiver(caregiver);
    }

    @Override
    public Optional<Caregiver> handle(GetCaregiverByIdQuery query) {
        return caregiverRepository.findById(query.caregiverId());
    }

    @Override
    public List<Caregiver> handle(GetCaregiverByLocationQuery query) {
        Sort sort = query.sort().isEmpty() ?  Sort.by(Sort.Order.asc("completeName")) : Sort.by(Sort.Order.desc(query.sort()));
        String district = query.district().isEmpty() ? "" : query.district();
        return caregiverRepository.findByDistrictScope(district, sort);
    }
}
