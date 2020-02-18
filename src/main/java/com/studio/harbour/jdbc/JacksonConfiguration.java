package com.studio.harbour.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {    	
        ObjectMapper mapper = new ObjectMapper();
    	mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE); 
    	mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        return mapper;
    }
}