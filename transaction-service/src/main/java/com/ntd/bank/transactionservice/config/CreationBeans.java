package com.ntd.bank.transactionservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreationBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
