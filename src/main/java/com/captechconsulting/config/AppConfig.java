package com.captechconsulting.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@PropertySource({"classpath:config.properties"})
@Import({DataConfig.class, WebConfig.class})
@ComponentScan(basePackages = {"com.captechconsulting"})
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}
