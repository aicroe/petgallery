package com.jalasoft.petgallery.cats;


import com.jalasoft.petgallery.termscore.Pair;
import com.jalasoft.petgallery.termscore.Term;
import com.jalasoft.petgallery.termscore.TermType;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class TermParser {

    public <T> Predicate<T> parse(Term term) {
        return object -> {
            if (term.hasValue()) {
                Pair pair = term.getValue();
                boolean matches = match(pair.getKey(), object, pair.getValue());
                return term.is(TermType.NOT) != matches;
            } else if (term.is(TermType.OR)) {
                return term.getChildren().stream()
                    .map(this::parse)
                    .reduce(Predicate::or)
                    .orElse(__ -> true)
                    .test(object);
            } else if (term.is(TermType.AND)) {
                return term.getChildren().stream()
                    .map(this::parse)
                    .reduce(Predicate::and)
                    .orElse(__ -> true)
                    .test(object);
            }
            return false;
        };
    }

    private boolean match(String fieldName, Object object, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return String.valueOf(field.get(object)).equals(String.valueOf(value));
        } catch (NoSuchFieldException e) {
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
