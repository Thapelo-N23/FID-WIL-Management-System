package za.ac.cput.repository;

import za.ac.cput.domain.ActivityLog;
import java.util.List;

public interface ActivityLogRepository extends IRepository<ActivityLog, Long> {
    List<ActivityLog> findByPerformedByOrderByPerformedAtDesc(String performedBy);
    List<ActivityLog> findByTargetUserOrderByPerformedAtDesc(String targetUser);
}