package co.posinvent.infrastructure.adapters.out.storage;

import co.posinvent.domain.exception.InvalidUploadException;
import co.posinvent.infrastructure.config.MediaProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductImageStorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void store_savesSupportedImageAndReturnsPublicUrl() throws Exception {
        var service = new ProductImageStorageService(new MediaProperties(tempDir));
        var file = new MockMultipartFile("file", "photo.png", "image/png", "image".getBytes());

        var imageUrl = service.store(file);

        assertThat(imageUrl).startsWith("/media/products/images/");
        var savedFile = tempDir.resolve(imageUrl.replaceFirst("^/media/", ""));
        assertThat(Files.exists(savedFile)).isTrue();
    }

    @Test
    void store_rejectsUnsupportedContentType() {
        var service = new ProductImageStorageService(new MediaProperties(tempDir));
        var file = new MockMultipartFile("file", "photo.gif", "image/gif", "image".getBytes());

        assertThatThrownBy(() -> service.store(file))
                .isInstanceOf(InvalidUploadException.class)
                .hasMessage("Solo se permiten imagenes JPG, PNG o WEBP.");
    }

    @Test
    void store_rejectsFilesBiggerThanTwoMegabytes() {
        var service = new ProductImageStorageService(new MediaProperties(tempDir));
        var file = new MockMultipartFile("file", "photo.webp", "image/webp", new byte[2 * 1024 * 1024 + 1]);

        assertThatThrownBy(() -> service.store(file))
                .isInstanceOf(InvalidUploadException.class)
                .hasMessage("La imagen no puede superar los 2 MB.");
    }
}
