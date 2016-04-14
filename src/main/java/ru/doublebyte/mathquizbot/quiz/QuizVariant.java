package ru.doublebyte.mathquizbot.quiz;

public class QuizVariant {

    private String name;
    private int value;

    public QuizVariant() {

    }

    public QuizVariant(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ". " + value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
