package com.jalasoft.petgallery.termscore.language;

import com.jalasoft.petgallery.termscore.Pair;
import com.jalasoft.petgallery.termscore.Term;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommaSeparatedLanguageTest {

    @Test
    public void parseListOfParams() {
        CommaSeparatedLanguage language = new CommaSeparatedLanguage();

        List<Term> terms = language.parse(List.of("name,Roger", "domain,example.org", "name,Tiger"));
        List<Term> expected = List.of(
            Term.value(new Pair("name", "Roger")),
            Term.value(new Pair("domain", "example.org")),
            Term.value(new Pair("name", "Tiger"))
        );

        assertEquals(expected, terms);
    }

    @Test
    public void parseReturnsEmptyList() {
        CommaSeparatedLanguage language = new CommaSeparatedLanguage();

        List<Term> terms = language.parse(List.of());

        assertEquals(terms, List.of());
    }

    @Test
    public void parseReturnsEmptyListForEmptyParams() {
        CommaSeparatedLanguage language = new CommaSeparatedLanguage();

        List<Term> terms = language.parse(List.of("", ""));

        assertEquals(terms, List.of());
    }

    @Test
    public void parseReturnsEmptyListForBlankParams() {
        CommaSeparatedLanguage language = new CommaSeparatedLanguage();

        List<Term> terms = language.parse(List.of("    ", " ", ""));

        assertEquals(terms, List.of());
    }

    @Test
    public void parseThrowsExWhenEmptyPairsArePassed() {
        CommaSeparatedLanguage language = new CommaSeparatedLanguage();

        assertThrows(LanguageSyntaxException.class, () -> language.parse(List.of(",")));
    }

    @Test
    public void parseThrowsExWhenBlankPairsArePassed() {
        CommaSeparatedLanguage language = new CommaSeparatedLanguage();

        assertThrows(LanguageSyntaxException.class, () -> language.parse(List.of("  ,   ")));
        assertThrows(LanguageSyntaxException.class, () -> language.parse(List.of(" key ,   ")));
        assertThrows(LanguageSyntaxException.class, () -> language.parse(List.of("  ,  value ")));
    }

    @Test
    public void parseTrimsThePairs() {
        CommaSeparatedLanguage language = new CommaSeparatedLanguage();

        List<Term> terms = language.parse(List.of(" key , value "));
        List<Term> expected = List.of(Term.value(new Pair("key", "value")));

        assertEquals(expected, terms);
    }

}