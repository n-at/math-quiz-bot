package ru.doublebyte.mathquizbot.quiz;

/**
 * Quiz difficulty levels
 */
public enum Level {

    Simple(2, 4, 10, 100),
    Medium(3, 4, 100, 1000),
    Hard(3, 4, 1000, 10000);

    Level(int numbersCount, int variantsCount, int minValue, int maxValue) {
        this.numbersCount = numbersCount;
        this.variantsCount = variantsCount;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private int numbersCount;
    private int variantsCount;
    private int minValue;
    private int maxValue;

    public int getNumbersCount() {
        return numbersCount;
    }

    public int getVariantsCount() {
        return variantsCount;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
