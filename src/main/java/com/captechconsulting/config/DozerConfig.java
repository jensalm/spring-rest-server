package com.captechconsulting.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;

@Configuration
public class DozerConfig {

    @Bean
    @Scope("singleton")
    public DozerBeanMapper getMapper() {
        return new DozerBeanMapper(
                Arrays.asList("dozer-global-configuration.xml")
        );
    }

}
