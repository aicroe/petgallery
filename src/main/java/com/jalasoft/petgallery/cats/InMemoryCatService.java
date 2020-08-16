package com.jalasoft.petgallery.cats;

import com.jalasoft.petgallery.termscore.UnknownTermPruner;
import com.jalasoft.petgallery.termscore.Term;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryCatService implements CatService {

    private static final List<Cat> CATS = List.of(
        new Cat("Riku", "siamese", "male", "brown", false),
        new Cat("Mikkan", "british", "male", "silver", true),
        new Cat("Black", "persian", "female", "white", true),
        new Cat("Tiger", "bengal", "male", "yellow", false),
        new Cat("Ruku", "persian", "female", "silver", true),
        new Cat("Orion", "siberian", "male", "brown", true),
        new Cat("Windy", "birman", "female", "white", true)
    );

    private TermParser termParser;
    private UnknownTermPruner termPruner;

    public InMemoryCatService(TermParser termParser, UnknownTermPruner termPruner) {
        this.termParser = termParser;
        this.termPruner = termPruner;
    }

    @Override
    public List<Cat> findAll() {
        return CATS;
    }

    @Override
    public List<Cat> findBy(Term filter) {
        Predicate<Cat> predicate = termParser.parse(termPruner.prune(filter, Cat.class));
        return CATS.stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }
}
