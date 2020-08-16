package com.jalasoft.petgallery.termscore;

import com.jalasoft.petgallery.termscore.language.Language;

import java.util.List;

public class Interpreter {

    private Language language;

    public Interpreter(Language language) {
        this.language = language;
    }

    public Term translate(List<String> params) {
        List<Term> terms = language.parse(params);
        for (KnownToken knownToken : language.getKnownTokens()) {
            terms = knownToken.process(terms);
        }
        return language.assembly(terms);
    }
}
