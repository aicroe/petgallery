package com.jalasoft.petgallery.termscore.language;

import com.jalasoft.petgallery.termscore.KnownToken;
import com.jalasoft.petgallery.termscore.Pair;
import com.jalasoft.petgallery.termscore.Term;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommaSeparatedLanguage extends Language {

    public CommaSeparatedLanguage(KnownToken... knownTokens) {
        super(knownTokens);
    }

    @Override
    public List<Term> parse(List<String> params) {
        return params.stream()
            .map(String::trim)
            .filter(Predicate.not(String::isEmpty))
            .map(param -> getPair(getTokens(param)))
            .map(Term::value)
            .collect(Collectors.toList());
    }

    @Override
    public Term assembly(List<Term> terms) {
        return Term.and(terms);
    }

    private String[] getTokens(String param) {
        String[] tokens = param.split(",");
        if (tokens.length < 2) {
            throw new LanguageSyntaxException("No key-value pairs found");
        }
        return tokens;
    }


    private Pair getPair(String[] tokens) {
        String field = tokens[0].trim();
        String value = String.join(",", Arrays.copyOfRange(tokens, 1, tokens.length)).trim();
        if (field.isEmpty() || value.isEmpty()) {
            throw new LanguageSyntaxException("No blank values allowed");
        }
        return new Pair(field, value);
    }

}
