package com.jalasoft.petgallery.controller;

import com.jalasoft.petgallery.cats.CatService;
import com.jalasoft.petgallery.cats.InMemoryCatService;
import com.jalasoft.petgallery.dogs.DogRepository;
import com.jalasoft.petgallery.dogs.DogService;
import com.jalasoft.petgallery.termscore.UnknownTermPruner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class GlobalProvider {

    @Bean
    public com.jalasoft.petgallery.cats.TermParser getCatsTermParser() {
        return new com.jalasoft.petgallery.cats.TermParser();
    }

    @Bean
    public com.jalasoft.petgallery.dogs.TermParser getDogsTermParser() {
        return new com.jalasoft.petgallery.dogs.TermParser();
    }

    @Bean
    public UnknownTermPruner getTermPruner() {
        return new UnknownTermPruner();
    }

    @Bean
    public CatService getCatService() {
        return new InMemoryCatService(getCatsTermParser(), getTermPruner());
    }

    @Bean()
    public DogService getDogService(EntityManager entityManager) {
        return new DogRepository(entityManager, getDogsTermParser(), getTermPruner());
    }
}
