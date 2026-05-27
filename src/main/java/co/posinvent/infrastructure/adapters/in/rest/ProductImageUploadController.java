package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductImageUploadResponse;
import co.posinvent.infrastructure.adapters.out.storage.ProductImageStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/uploads/products/images")
public class ProductImageUploadController {

    private final ProductImageStorageService productImageStorageService;

    public ProductImageUploadController(ProductImageStorageService productImageStorageService) {
        this.productImageStorageService = productImageStorageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductImageUploadResponse> upload(@RequestPart("file") MultipartFile file) {
        var imageUrl = productImageStorageService.store(file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProductImageUploadResponse(imageUrl));
    }
}
