package co.posinvent.domain.repository;

import co.posinvent.domain.model.AdvanceApplication;

import java.util.List;
import java.util.UUID;

public interface AdvanceApplicationRepository {

    AdvanceApplication save(AdvanceApplication application);

    List<AdvanceApplication> findByAdvancePaymentId(UUID advancePaymentId);
}
