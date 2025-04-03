package co.edu.uniquindio.ingesis.restful.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;

public class AuditLog {
    public static final Logger AUDIT_LOG = LoggerFactory.getLogger("audit");

    public static void logAction(String user, String message, Object... args) {
        MDC.put("user", user);
        AUDIT_LOG.warn(message, args);
        MDC.clear();
    }
}
