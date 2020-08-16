package com.jalasoft.petgallery.termscore;

import com.jalasoft.petgallery.termscore.language.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    public void processEmptyParam() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage());

        Term actual = interpreter.translate(List.of(""));
        Term expected = Term.and();

        assertEquals(actual, expected);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void processEmptyPairsThrowsException() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage());

        assertThrows(LanguageSyntaxException.class, () -> interpreter.translate(List.of(",,,,,")));
    }

    @Test
    public void processSingleParam() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage());

        Term actual = interpreter.translate(List.of("name,Roger"));
        Term expected = Term.and(Term.value(new Pair("name", "Roger")));

        assertEquals(expected, actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    public void processParamWithCommasInValue() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage());

        Term actual = interpreter.translate(List.of("text,my name is Deliora, the dragon"));
        Term expected = Term.and(Term.value(new Pair("text", "my name is Deliora, the dragon")));

        assertEquals(expected, actual);
    }

    @Test
    public void processMultipleParams() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage());

        Term actual = interpreter.translate(List.of("name,Roger", "date,05-05-2020", "domain,example.org"));
        Term expected = Term.and(
            Term.value(new Pair("name", "Roger")),
            Term.value(new Pair("date", "05-05-2020")),
            Term.value(new Pair("domain", "example.org"))
        );

        assertEquals(expected, actual);
    }

    @Test
    public void processParamWithNegation() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage(new NotToken()));

        Term actual = interpreter.translate(List.of("name,Roger", "date,!05-05-2020", "domain,example.org"));
        Term expected = Term.and(
            Term.value(new Pair("name", "Roger")),
            Term.not(new Pair("date", "05-05-2020")),
            Term.value(new Pair("domain", "example.org"))
        );

        assertEquals(expected, actual);
    }

    @Test
    public void processSpreadKeyDuplicatedParams() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage(new SpreadOrToken()));

        Term actual = interpreter.translate(List.of(
            "name,Roger",
            "name,Racer",
            "name,!Duck"));
        Term expected = Term.and(
            Term.or(
                Term.value(new Pair("name", "Roger")),
                Term.value(new Pair("name", "Racer")),
                Term.value(new Pair("name", "!Duck"))
            )
        );

        assertEquals(expected, actual);
    }

    @Test
    public void processMultipleSpreadParams() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage(new SpreadOrToken()));

        Term actual = interpreter.translate(List.of(
            "name,Roger",
            "domain,example.org",
            "name,Racer",
            "name,!Duck",
            "domain,silver.example.org"));
        Term expected = Term.and(
            Term.or(
                Term.value(new Pair("domain", "example.org")),
                Term.value(new Pair("domain", "silver.example.org"))
            ),
            Term.or(
                Term.value(new Pair("name", "Roger")),
                Term.value(new Pair("name", "Racer")),
                Term.value(new Pair("name", "!Duck"))
            )
        );

        assertEquals(expected, actual);
    }

    @Test
    public void processNegationInComposedTerm() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage(new SpreadOrToken(), new NotToken()));

        Term actual = interpreter.translate(List.of(
            "name,Roger",
            "domain,!example.org",
            "name,Racer",
            "name,!Duck",
            "domain,silver.example.org"));
        Term expected = Term.and(
            Term.or(
                Term.not(new Pair("domain", "example.org")),
                Term.value(new Pair("domain", "silver.example.org"))
            ),
            Term.or(
                Term.value(new Pair("name", "Roger")),
                Term.value(new Pair("name", "Racer")),
                Term.not(new Pair("name", "Duck"))
            )
        );

        assertEquals(expected, actual);
    }

    @Test
    public void processWithDisjunction() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage(new OrToken()));

        Term actual = interpreter.translate(List.of("name,Roger|Racer"));
        Term expected = Term.and(
            Term.or(
                Term.value(new Pair("name", "Roger")),
                Term.value(new Pair("name", "Racer"))
            )
        );

        assertEquals(expected, actual);
    }

    @Test
    public void processDisjunctionsFromMultipleSources() {
        Interpreter interpreter = new Interpreter(new CommaSeparatedLanguage(new OrToken(), new SpreadOrToken()));

        Term actual = interpreter.translate(List.of("name,Roger|Racer", "color,red", "name,Zero", "name,Travis"));
        Term expected = Term.and(
            Term.value(new Pair("color", "red")),
            Term.or(
                Term.value(new Pair("name", "Roger")),
                Term.value(new Pair("name", "Racer")),
                Term.value(new Pair("name", "Zero")),
                Term.value(new Pair("name", "Travis"))
            )
        );

        assertEquals(expected, actual);
    }
}