package com.jalasoft.petgallery.termscore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnknownTermPrunerTest {

    @Test
    public void pruneUnknownTerm() {
        UnknownTermPruner pruner = new UnknownTermPruner();

        Term result = pruner.prune(Term.value(new Pair("name", "CarloMagno")), CarUnderTesting.class);

        assertNull(result);
    }

    @Test
    public void pruneUnknownChildTerms() {
        UnknownTermPruner pruner = new UnknownTermPruner();

        Term result = pruner.prune(
            Term.and(
                Term.value(new Pair("seats", "2")),
                Term.value(new Pair("name", "CarloMagno")),
                Term.value(new Pair("model", "2020")),
                Term.value(new Pair("name", "Rafael")),
                Term.value(new Pair("year", "2020"))
            ),
            CarUnderTesting.class
        );
        Term expected = Term.and(
            Term.value(new Pair("seats", "2")),
            Term.value(new Pair("model", "2020"))
        ) ;

        assertEquals(expected, result);
    }

    @Test
    public void pruneUnknownNestedChildTerms() {
        UnknownTermPruner pruner = new UnknownTermPruner();

        Term result = pruner.prune(
            Term.and(
                Term.not(new Pair("seats", "2")),
                Term.or(
                    Term.value(new Pair("name", "CarloMagno")),
                    Term.value(new Pair("name", "Rafael"))
                ),
                Term.and(
                    Term.value(new Pair("year", "2015")),
                    Term.value(new Pair("year", "2020"))
                ),
                Term.or(
                    Term.value(new Pair("model", "2020")),
                    Term.value(new Pair("model", "2015"))
                )
            ),
            CarUnderTesting.class
        );
        Term expected = Term.and(
            Term.not(new Pair("seats", "2")),
            Term.or(
                Term.value(new Pair("model", "2020")),
                Term.value(new Pair("model", "2015"))
            )
        ) ;

        assertEquals(expected, result);
    }

    private class CarUnderTesting {
        private String model;
        private int seats;
    }
}