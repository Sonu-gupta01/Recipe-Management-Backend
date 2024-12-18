package com.recipe.management.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.Converter;
import org.hibernate.collection.spi.PersistentSet;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // Custom converter to handle PersistentSet conversion
        modelMapper.addConverter(new Converter<PersistentSet, List<Object>>() {
            public List<Object> convert(MappingContext<PersistentSet, List<Object>> context) {
                return new ArrayList<>(context.getSource());
            }
        });
        return modelMapper;
    }

}

