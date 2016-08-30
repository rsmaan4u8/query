package com.api.config;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertTrue;

public class MappingJackson2HttpMessageConverterExtensionTest {

    @Test
    public void testConstructor_shouldAddJavascriptMediaType() {
        MappingJackson2HttpMessageConverterExtension converterExtension = new MappingJackson2HttpMessageConverterExtension();

        assertTrue(converterExtension.getSupportedMediaTypes().contains(new MediaType("text", "javascript")));
    }

}