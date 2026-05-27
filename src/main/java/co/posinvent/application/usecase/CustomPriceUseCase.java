package co.posinvent.application.usecase;

import co.posinvent.application.annotation.Auditable;
import co.posinvent.application.dto.CustomPriceRequest;
import co.posinvent.application.dto.CustomPriceResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.model.CustomPrice;
import co.posinvent.domain.repository.CustomPriceRepository;
import co.posinvent.domain.repository.ProductRepository;
import co.posinvent.domain.repository.ThirdPartyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomPriceUseCase {

    private final CustomPriceRepository repository;
    private final ThirdPartyRepository thirdPartyRepository;
    private final ProductRepository productRepository;

    public CustomPriceUseCase(
            CustomPriceRepository repository,
            ThirdPartyRepository thirdPartyRepository,
            ProductRepository productRepository
    ) {
        this.repository = repository;
        this.thirdPartyRepository = thirdPartyRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomPriceResponse> listAll(UUID clientId, UUID productId) {
        List<CustomPrice> prices;

        if (clientId != null && productId != null) {
            prices = repository.findByClientIdAndProductId(clientId, productId)
                    .map(List::of).orElse(List.of());
        } else if (clientId != null) {
            prices = repository.findByClientId(clientId);
        } else if (productId != null) {
            prices = repository.findByProductId(productId);
        } else {
            prices = repository.findAll();
        }

        return enrichResponses(prices);
    }

    @Auditable(entityType = "CUSTOM_PRICE", action = "CREATE")
    @Transactional
    public CustomPriceResponse create(CustomPriceRequest request) {
        validateClientExists(request.clientId());
        validateProductExists(request.productId());
        validateTaxType(request.taxType());

        if (repository.existsByClientIdAndProductId(request.clientId(), request.productId())) {
            throw new BusinessException(
                    "DUPLICATE_CUSTOM_PRICE",
                    "Custom price already exists for this client and product"
            );
        }

        var customPrice = new CustomPrice(
                null,
                request.clientId(),
                request.productId(),
                request.price(),
                request.taxType(),
                request.taxRate()
        );

        var saved = repository.save(customPrice);
        return buildResponse(saved);
    }

    @Auditable(entityType = "CUSTOM_PRICE", action = "UPDATE")
    @Transactional
    public CustomPriceResponse update(UUID id, CustomPriceRequest request) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Custom price", id));

        validateClientExists(request.clientId());
        validateProductExists(request.productId());
        validateTaxType(request.taxType());

        // Check duplicate if client or product changed
        if (!existing.clientId().equals(request.clientId())
                || !existing.productId().equals(request.productId())) {
            if (repository.existsByClientIdAndProductId(request.clientId(), request.productId())) {
                throw new BusinessException(
                        "DUPLICATE_CUSTOM_PRICE",
                        "Custom price already exists for this client and product"
                );
            }
        }

        var updated = new CustomPrice(
                id,
                request.clientId(),
                request.productId(),
                request.price(),
                request.taxType(),
                request.taxRate()
        );

        var saved = repository.save(updated);
        return buildResponse(saved);
    }

    @Auditable(entityType = "CUSTOM_PRICE", action = "DELETE")
    @Transactional
    public void delete(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Custom price", id);
        }
        repository.delete(id);
    }

    private void validateClientExists(UUID clientId) {
        if (thirdPartyRepository.findById(clientId).isEmpty()) {
            throw new BusinessException("CLIENT_NOT_FOUND", "Client not found: " + clientId);
        }
    }

    private void validateProductExists(UUID productId) {
        if (productRepository.findById(productId).isEmpty()) {
            throw new BusinessException("PRODUCT_NOT_FOUND", "Product not found: " + productId);
        }
    }

    private void validateTaxType(String taxType) {
        var validTypes = List.of("IVA", "INC", "EXENTO");
        if (!validTypes.contains(taxType)) {
            throw new BusinessException("INVALID_TAX_TYPE", "invalid tax_type: " + taxType);
        }
    }

    private List<CustomPriceResponse> enrichResponses(List<CustomPrice> prices) {
        if (prices.isEmpty()) return List.of();

        var clientIds = prices.stream().map(CustomPrice::clientId).distinct().toList();
        var productIds = prices.stream().map(CustomPrice::productId).distinct().toList();

        Map<UUID, String> clientNames = clientIds.stream()
                .map(thirdPartyRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .collect(Collectors.toMap(
                        tp -> tp.id(),
                        tp -> tp.name()
                ));

        Map<UUID, String> productNames = productIds.stream()
                .map(productRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .collect(Collectors.toMap(
                        p -> p.id(),
                        p -> p.name()
                ));

        return prices.stream()
                .map(cp -> new CustomPriceResponse(
                        cp.id(),
                        cp.clientId(),
                        clientNames.getOrDefault(cp.clientId(), null),
                        cp.productId(),
                        productNames.getOrDefault(cp.productId(), null),
                        cp.price(),
                        cp.taxType(),
                        cp.taxRate()
                ))
                .toList();
    }

    private CustomPriceResponse buildResponse(CustomPrice cp) {
        var client = thirdPartyRepository.findById(cp.clientId()).orElse(null);
        var product = productRepository.findById(cp.productId()).orElse(null);
        return CustomPriceResponse.from(cp, client, product);
    }
}