package com.jalasoft.petgallery.controller;

import com.jalasoft.petgallery.termscore.Interpreter;
import com.jalasoft.petgallery.termscore.InterpreterFactory;
import com.jalasoft.petgallery.termscore.Term;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ApiConfigurer implements WebMvcConfigurer  {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        Interpreter interpreter = InterpreterFactory.get();

        registry.addConverter(new Converter<String, Term>() {
            @Override
            public Term convert(String source) {
                return interpreter.translate(List.of(source));
            }
        });

        registry.addConverter(new Converter<String[], Term>() {
            @Override
            public Term convert(String[] source) {
                return interpreter.translate(List.of(source));
            }
        });
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
}
