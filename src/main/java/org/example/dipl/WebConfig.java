package org.example.dipl;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Додати доступ до статичних ресурсів з папки static
        registry.addResourceHandler("/static/images/**")
                .addResourceLocations("classpath:/static/images/");

        // Додати доступ до файлів з папки uploads/images, що знаходиться в корені проєкту
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");  // Використовуємо file: для доступу до файлової системи
    }
}
