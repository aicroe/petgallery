package com.jalasoft.petgallery.dogs;

import com.jalasoft.petgallery.termscore.Pair;
import com.jalasoft.petgallery.termscore.Term;
import com.jalasoft.petgallery.termscore.TermType;

import java.util.function.Predicate;

public class TermParser {

    public String parse(Term term) {
        if (term.hasValue()) {
            Pair pair = term.getValue();
            return String.format("d.%s = '%s'", pair.getKey(), pair.getValue());
        } else if (term.is(TermType.OR)) {
            return term.getChildren().stream()
                .map(this::parse)
                .filter(Predicate.not(String::isEmpty))
                .reduce((expression, current) -> String.format("%s OR %s", expression, current))
                .map(expression -> String.format("(%s)", expression))
                .orElse("");
        } else if (term.is(TermType.AND)) {
            return term.getChildren().stream()
                .map(this::parse)
                .reduce((expression, current) -> String.format("%s AND %s", expression, current))
                .orElse("");
        }
        return "";
    }
}