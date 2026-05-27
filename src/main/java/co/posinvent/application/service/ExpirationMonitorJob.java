package co.posinvent.application.service;

import co.posinvent.domain.repository.StockDisposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExpirationMonitorJob {

    private static final Logger log = LoggerFactory.getLogger(ExpirationMonitorJob.class);
    private final StockDisposalRepository disposalRepo;

    public ExpirationMonitorJob(StockDisposalRepository disposalRepo) {
        this.disposalRepo = disposalRepo;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void checkExpiringBatches() {
        var expiring = disposalRepo.findExpiringBatches(30);
        if (!expiring.isEmpty()) {
            log.warn("Hay {} lotes próximos a vencer (30 días):", expiring.size());
            for (var batch : expiring) {
                log.info("  Lote {} — producto {} — bodega {} — vence: {}",
                        batch.get("batch_id"), batch.get("product_name"),
                        batch.get("warehouse_name"), batch.get("expiration_date"));
            }
        }
    }
}
