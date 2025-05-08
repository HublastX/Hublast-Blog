package com.hublast.api_blog.mapper;

import java.lang.reflect.Method;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.addConverter(mappingContext -> {
            Object source = mappingContext.getSource();
            if (source == null) return null;

            try {
                Method getIdMethod = source.getClass().getMethod("getId");
                return getIdMethod.invoke(source);
            } catch (NoSuchMethodException e) {
                return source; // Mantém o valor original se não houver getId()
            } catch (Exception e) {
                return null;
            }
        });

        return modelMapper;
    }

}
