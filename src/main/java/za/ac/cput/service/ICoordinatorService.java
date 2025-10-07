package za.ac.cput.service;

import za.ac.cput.domain.Coordinator;
import za.ac.cput.dto.CoordinatorRequest;

public interface ICoordinatorService extends IService<Coordinator, Long> {
    Coordinator registerCoordinator(CoordinatorRequest request, String adminEmail);
}