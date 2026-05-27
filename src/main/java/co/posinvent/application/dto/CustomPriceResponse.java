package co.posinvent.application.dto;

import co.posinvent.domain.model.CustomPrice;
import co.posinvent.domain.model.Product;
import co.posinvent.domain.model.ThirdParty;

import java.math.BigDecimal;
import java.util.UUID;

public record CustomPriceResponse(
        UUID id,
        UUID clientId,
        String clientName,
        UUID productId,
        String productName,
        BigDecimal price,
        String taxType,
        BigDecimal taxRate
) {
    public static CustomPriceResponse from(CustomPrice cp, ThirdParty client, Product product) {
        return new CustomPriceResponse(
                cp.id(),
                cp.clientId(),
                client != null ? client.name() : null,
                cp.productId(),
                product != null ? product.name() : null,
                cp.price(),
                cp.taxType(),
                cp.taxRate()
        );
    }
}