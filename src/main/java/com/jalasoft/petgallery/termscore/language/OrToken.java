package com.jalasoft.petgallery.termscore.language;

import com.jalasoft.petgallery.termscore.KnownToken;
import com.jalasoft.petgallery.termscore.Pair;
import com.jalasoft.petgallery.termscore.Term;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OrToken implements KnownToken {

    public static final String DISJUNCTION = "|";

    @Override
    public List<Term> process(List<Term> terms) {
        Function<Term, Term> mapOr = term -> {
            if (term.hasValue()) {
                Pair pair = term.getValue();
                if (pair.getValue().contains(DISJUNCTION)) {
                    String[] values = pair.getValue().split(Pattern.quote(DISJUNCTION));
                    return Term.or(
                        List.of(values).stream()
                            .map(value -> Term.value(new Pair(pair.getKey(), value)))
                            .toArray(Term[]::new)
                    );
                }
            } else {
                return term.setChildren(process(term.getChildren()));
            }
            return term;
        };

        return terms.stream().map(mapOr).collect(Collectors.toList());
    }
}
