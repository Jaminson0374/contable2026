package co.posinvent.domain.repository;

import co.posinvent.domain.model.TransportGuide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TransportGuideRepository {

    TransportGuide save(TransportGuide guide);

    Optional<TransportGuide> findById(UUID id);

    Page<TransportGuide> findAll(Pageable pageable);
}
