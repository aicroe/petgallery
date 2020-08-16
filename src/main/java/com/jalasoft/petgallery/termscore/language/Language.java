package com.jalasoft.petgallery.termscore.language;

import com.jalasoft.petgallery.termscore.KnownToken;
import com.jalasoft.petgallery.termscore.Term;

import java.util.List;

public abstract class Language {

    private List<KnownToken> knownTokens;

    public Language(KnownToken ...knownTokens) {
        this.knownTokens = List.of(knownTokens);
    }

    public List<KnownToken> getKnownTokens() {
        return this.knownTokens;
    }

    public abstract List<Term> parse(List<String> params);

    public abstract Term assembly(List<Term> terms);
}
