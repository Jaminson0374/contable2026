package co.posinvent.infrastructure.adapters.out.storage;

import co.posinvent.domain.exception.InvalidUploadException;
import co.posinvent.domain.exception.StorageException;
import co.posinvent.infrastructure.config.MediaProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductImageStorageService {

    private static final long MAX_FILE_SIZE_BYTES = 2L * 1024 * 1024;
    private static final Map<String, String> EXTENSIONS_BY_CONTENT_TYPE = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/webp", "webp"
    );

    private final java.nio.file.Path productImagesDirectory;

    public ProductImageStorageService(MediaProperties mediaProperties) {
        this.productImagesDirectory = mediaProperties.storagePath()
                .toAbsolutePath()
                .normalize()
                .resolve("products")
                .resolve("images");
    }

    public String store(MultipartFile file) {
        validate(file);

        var extension = EXTENSIONS_BY_CONTENT_TYPE.get(file.getContentType());
        var filename = UUID.randomUUID() + "." + extension;
        var target = productImagesDirectory.resolve(filename).normalize();

        try {
            Files.createDirectories(productImagesDirectory);
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new StorageException("UPLOAD_FAILED", "No fue posible guardar la imagen.", ex);
        }

        return "/media/products/images/" + filename;
    }

    private void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidUploadException("INVALID_UPLOAD", "Debe seleccionar una imagen.");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new InvalidUploadException("INVALID_UPLOAD", "La imagen no puede superar los 2 MB.");
        }

        if (!EXTENSIONS_BY_CONTENT_TYPE.containsKey(file.getContentType())) {
            throw new InvalidUploadException(
                    "INVALID_UPLOAD",
                    "Solo se permiten imagenes JPG, PNG o WEBP."
            );
        }
    }
}
