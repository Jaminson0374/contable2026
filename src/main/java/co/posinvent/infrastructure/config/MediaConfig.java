package co.posinvent.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MediaConfig implements WebMvcConfigurer {

    private final String mediaLocation;

    public MediaConfig(MediaProperties mediaProperties) {
        var location = mediaProperties.storagePath().toAbsolutePath().normalize().toUri().toString();
        this.mediaLocation = location.endsWith("/") ? location : location + "/";
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/media/**")
                .addResourceLocations(mediaLocation);
    }
}
