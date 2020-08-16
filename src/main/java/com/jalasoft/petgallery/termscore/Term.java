package com.jalasoft.petgallery.termscore;

import java.util.List;

public class Term {

    private final TermType type;
    private Pair value;
    private List<Term> children;

    private Term(TermType type, Pair value) {
        this.type = type;
        this.value = value;
    }

    private Term(TermType type, List<Term> children) {
        this.type = type;
        this.children = children;
    }

    public static Term value(Pair value) {
        return new Term(TermType.VALUE, value);
    }

    public static Term and(Term... terms) {
        return new Term(TermType.AND, List.of(terms));
    }

    public static Term and(List<Term> terms) {
        return new Term(TermType.AND, List.copyOf(terms));
    }

    public static Term or(Term... terms) {
        return new Term(TermType.OR, List.of(terms));
    }

    public static Term or(List<Term> terms) {
        return new Term(TermType.OR, List.copyOf(terms));
    }

    public static Term not(Pair value) {
        return new Term(TermType.NOT, value);
    }

    public boolean hasValue() {
        return type == TermType.VALUE || type == TermType.NOT;
    }

    public boolean isComposed() {
        return type == TermType.AND || type == TermType.OR;
    }

    public boolean is(TermType type) {
        return this.type == type;
    }

    public Pair getValue() {
        return value;
    }

    public List<Term> getChildren() {
        return List.copyOf(children);
    }

    public Term setChildren(List<Term> children) {
        return new Term(type, List.copyOf(children));
    }

    public boolean isEmpty() {
        return !hasValue() && children.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Term)) {
            return false;
        }
        Term term = (Term) obj;
        boolean composed = isComposed();
        return type.equals(term.type)
            && (composed ? value == term.value : value.equals(term.value))
            && (composed ? children.equals(term.children) : children == term.children);
    }

    @Override
    public String toString() {
        return switch (type) {
            case VALUE -> value.toString();
            case NOT -> ("!" + value.toString());
            case AND -> children.stream()
                .map(Term::toString)
                .reduce((result, current) -> String.format("%s && %s", result, current))
                .map(result -> String.format("(%s)", result))
                .orElse("");
            case OR -> children.stream()
                .map(Term::toString)
                .reduce((result, current) -> String.format("%s || %s", result, current))
                .map(result -> String.format("(%s)", result))
                .orElse("");
        };
    }
}
