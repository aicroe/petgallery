package com.jalasoft.petgallery.termscore;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnknownTermPruner {

    public Term prune(Term term, Class<?> representation) {
        List<String> fields = getKnownFields(representation);
        return prune(term, fields);
    }

    private Term prune(Term term, List<String> knownFields) {
        if (term.hasValue()) {
            Pair pair = term.getValue();
            if (knownFields.contains(pair.getKey())) {
                return term;
            } else {
                return null;
            }
        } else {
            List<Term> children = term.getChildren().stream()
                .map(child -> prune(child, knownFields))
                .filter(child -> {
                    if (child == null) {
                        return false;
                    }
                    return child.hasValue() || !child.isEmpty();
                })
                .collect(Collectors.toList());
            return term.setChildren(children);
        }
    }

    private List<String> getKnownFields(Class<?> representation) {
        return Arrays.stream(representation.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toList());
    }
}
