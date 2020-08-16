package com.jalasoft.petgallery.dogs;

import com.jalasoft.petgallery.termscore.Term;

import java.util.List;

public interface DogService {
    List<Dog> findAll();
    List<Dog> findBy(Term filter);
}
