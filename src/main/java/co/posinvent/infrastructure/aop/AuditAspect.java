package co.posinvent.infrastructure.aop;

import co.posinvent.application.annotation.Auditable;
import co.posinvent.domain.model.AuditLog;
import co.posinvent.domain.repository.AuditLogRepository;
import co.posinvent.infrastructure.adapters.out.security.PosUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.util.UUID;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    private final AuditLogRepository auditLogRepository;

    public AuditAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        String entityType = auditable.entityType();
        String action = resolveAction(auditable.action(), joinPoint);
        Object[] args = joinPoint.getArgs();
        UUID entityId = null;
        String oldValue = null;
        String newValue = null;

        // Capture pre-execution context
        try {
            // For UPDATE/DELETE: entity ID is typically the first UUID argument
            if (("UPDATE".equals(action) || "DELETE".equals(action)) && args.length > 0) {
                if (args[0] instanceof UUID id) {
                    entityId = id;
                }
            }

            // For UPDATE: capture old state from request arguments
            if ("UPDATE".equals(action) && args.length > 1) {
                newValue = safeToString(args[1]);
            } else if ("DELETE".equals(action) && entityId != null) {
                oldValue = "ID: " + entityId;
            }
        } catch (Exception e) {
            log.warn("Failed to capture pre-audit context for entity={} action={}: {}",
                    entityType, action, e.getMessage());
        }

        // Execute the method — if it throws, no audit is written
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable t) {
            // Failed operation — do NOT audit, just propagate
            throw t;
        }

        // Capture post-execution context and persist audit log
        try {
            // For CREATE: entity ID comes from the response
            if ("CREATE".equals(action) && result != null) {
                entityId = extractId(result);
                if (args.length > 0) {
                    newValue = safeToString(args[0]);
                }
            }

            // For UPDATE: entity id already captured; result confirms success
            if ("UPDATE".equals(action) && result != null && entityId == null) {
                entityId = extractId(result);
            }

            UUID userId = extractCurrentUserId();
            String ipAddress = extractClientIp();

            var auditLog = new AuditLog(
                    null,
                    entityType,
                    entityId,
                    action,
                    null,
                    oldValue,
                    newValue,
                    userId,
                    ipAddress,
                    OffsetDateTime.now()
            );

            auditLogRepository.save(auditLog);

            log.debug("Audit logged: entity={} id={} action={} user={}",
                    entityType, entityId, action, userId);

        } catch (Exception e) {
            // Audit failure must not break business logic
            log.error("Failed to persist audit log for entity={} action={}: {}",
                    entityType, action, e.getMessage(), e);
        }

        return result;
    }

    // ── Helpers ────────────────────────────────────────────────────────

    private String resolveAction(String annotationAction, ProceedingJoinPoint joinPoint) {
        if (annotationAction != null && !annotationAction.isBlank()) {
            return annotationAction;
        }
        // Auto-detect from method name
        String methodName = joinPoint.getSignature().getName().toUpperCase();
        if (methodName.contains("CREATE") || methodName.contains("SAVE")) {
            return "CREATE";
        }
        if (methodName.contains("UPDATE") || methodName.contains("MODIFY")) {
            return "UPDATE";
        }
        if (methodName.contains("DELETE") || methodName.contains("REMOVE")
                || methodName.contains("CANCEL") || methodName.contains("DEACTIVATE")) {
            return "DELETE";
        }
        return "UNKNOWN";
    }

    private UUID extractId(Object obj) {
        try {
            Method idMethod = obj.getClass().getMethod("id");
            Object value = idMethod.invoke(obj);
            if (value instanceof UUID uuid) {
                return uuid;
            }
            if (value != null) {
                return UUID.fromString(value.toString());
            }
        } catch (NoSuchMethodException e) {
            // Object has no id() method — try getId() as fallback
            try {
                Method getIdMethod = obj.getClass().getMethod("getId");
                Object value = getIdMethod.invoke(obj);
                if (value instanceof UUID uuid) {
                    return uuid;
                }
                if (value != null) {
                    return UUID.fromString(value.toString());
                }
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private UUID extractCurrentUserId() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof PosUserDetails pud) {
                return pud.userId();
            }
        } catch (Exception e) {
            log.warn("Could not extract user ID from security context", e);
        }
        return null;
    }

    private String extractClientIp() {
        try {
            var attrs = RequestContextHolder.getRequestAttributes();
            if (attrs instanceof ServletRequestAttributes servletAttrs) {
                HttpServletRequest request = servletAttrs.getRequest();
                String xForwardedFor = request.getHeader("X-Forwarded-For");
                if (xForwardedFor != null && !xForwardedFor.isBlank()) {
                    return xForwardedFor.split(",")[0].trim();
                }
                return request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.debug("Could not extract client IP", e);
        }
        return null;
    }

    private String safeToString(Object obj) {
        if (obj == null) return null;
        try {
            return obj.toString();
        } catch (Exception e) {
            return obj.getClass().getSimpleName();
        }
    }
}
