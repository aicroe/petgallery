package com.jalasoft.petgallery.termscore.language;

import com.jalasoft.petgallery.termscore.KnownToken;
import com.jalasoft.petgallery.termscore.Pair;
import com.jalasoft.petgallery.termscore.Term;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NotToken implements KnownToken {

    public static final String NEGATION = "!";

    @Override
    public List<Term> process(List<Term> terms) {
        Function<Term, Term> mapNot = term -> {
            if (term.hasValue()) {
                Pair pair = term.getValue();
                if (pair.getValue().startsWith(NEGATION)) {
                    String value = pair.getValue().substring(1);
                    return Term.not(pair.setValue(value));
                }
            } else {
                return term.setChildren(process(term.getChildren()));
            }
            return term;
        };

        return terms.stream().map(mapNot).collect(Collectors.toList());
    }
}
