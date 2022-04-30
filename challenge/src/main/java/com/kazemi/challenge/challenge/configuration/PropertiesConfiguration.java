package com.kazemi.challenge.challenge.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:application-prod.properties"),
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-dev.properties"),
        @PropertySource("classpath:application-test.properties")
})
@Data
public class PropertiesConfiguration {
    @Value( "${spring.web.locale}" )
    private String local;
}
