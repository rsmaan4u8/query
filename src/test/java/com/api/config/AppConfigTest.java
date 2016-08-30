package com.api.config;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;

public class AppConfigTest {

    @Test
    public void testRestTemplate_shouldAddExtendedJsonConverter() throws Exception {

        RestTemplate restTemplate = new AppConfig().restTemplate();

        assertTrue(restTemplate.getMessageConverters().stream().anyMatch(p -> p instanceof MappingJackson2HttpMessageConverterExtension));
    }
}