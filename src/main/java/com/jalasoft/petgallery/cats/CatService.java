package com.jalasoft.petgallery.cats;

import com.jalasoft.petgallery.termscore.Term;

import java.util.List;

public interface CatService {
    List<Cat> findAll();
    List<Cat> findBy(Term filter);
}
