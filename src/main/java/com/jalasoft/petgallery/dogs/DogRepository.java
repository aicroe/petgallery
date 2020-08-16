package com.jalasoft.petgallery.dogs;

import com.jalasoft.petgallery.termscore.UnknownTermPruner;
import com.jalasoft.petgallery.termscore.Term;

import javax.persistence.EntityManager;
import java.util.List;

public class DogRepository implements DogService {

    private EntityManager entityManager;
    private TermParser termParser;
    private UnknownTermPruner termPruner;

    public DogRepository(EntityManager entityManager, TermParser termParser, UnknownTermPruner termPruner) {
        this.entityManager = entityManager;
        this.termParser = termParser;
        this.termPruner = termPruner;
    }

    @Override
    public List<Dog> findAll() {
        return entityManager.createQuery("SELECT d FROM Dog d", Dog.class).getResultList();
    }

    @Override
    public List<Dog> findBy(Term filter) {
        String parsed = termParser.parse(termPruner.prune(filter, Dog.class));
        String where = "";
        if (!parsed.isEmpty()) {
            where = String.format("WHERE %s", parsed);
        }
        return entityManager.createQuery(String.format("SELECT d FROM Dog d %s", where), Dog.class).getResultList();
    }
}
