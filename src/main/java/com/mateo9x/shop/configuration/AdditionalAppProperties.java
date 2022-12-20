package com.mateo9x.shop.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Configuration
@Validated
@Data
@PropertySource("classpath:application.properties")
public class AdditionalAppProperties {

    @NotNull
    @Value("${shop-app.frontend.url}")
    private String frontendUrl;

    @NotNull
    @Value("${shop-app.photo.path.url}")
    private String photoPathUrl;
}
