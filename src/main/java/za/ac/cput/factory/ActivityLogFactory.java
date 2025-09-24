package za.ac.cput.factory;

import za.ac.cput.domain.ActivityLog;
import za.ac.cput.util.Helper;

public class ActivityLogFactory {

    public static ActivityLog createActivityLog(String action, String description,
                                                String performedBy, String targetUser) {
        if (Helper.isNullOrEmpty(action) || Helper.isNullOrEmpty(description) ||
                Helper.isNullOrEmpty(performedBy)) {
            throw new IllegalArgumentException("Action, description, and performedBy are required");
        }

        return new ActivityLog.Builder()
                .setAction(action)
                .setDescription(description)
                .setPerformedBy(performedBy)
                .setTargetUser(targetUser)
                .build();
    }

    public static ActivityLog createActivityLog(String action, String description,
                                                String performedBy, String targetUser, String ipAddress) {
        ActivityLog log = createActivityLog(action, description, performedBy, targetUser);
        return new ActivityLog.Builder()
                .copy(log)
                .setIpAddress(ipAddress)
                .build();
    }
}