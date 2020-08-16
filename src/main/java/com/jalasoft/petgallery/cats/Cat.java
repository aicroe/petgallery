package com.jalasoft.petgallery.cats;

public class Cat {

    private String name;
    private String breed;
    private String gender;
    private String color;
    private boolean fluffy;

    public Cat(String name, String breed, String gender, String color, boolean fluffy) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.color = color;
        this.fluffy = fluffy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFluffy() {
        return fluffy;
    }

    public void setFluffy(boolean fluffy) {
        this.fluffy = fluffy;
    }
}
