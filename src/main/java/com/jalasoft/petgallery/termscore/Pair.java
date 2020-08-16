package com.jalasoft.petgallery.termscore;

public class Pair {

    private String key;
    private String value;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Pair setValue(String value) {
        return new Pair(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair pair = (Pair) obj;
        return key.equals(pair.key) && value.equals(pair.value);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", key, value);
    }
}
