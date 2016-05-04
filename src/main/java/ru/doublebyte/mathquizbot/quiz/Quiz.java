package ru.doublebyte.mathquizbot.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Quiz {

    private static final Random random = new Random();

    private List<Integer> numbers;
    private List<Integer> variants;
    private int answer;


    private Quiz() {

    }

    public Quiz(Level level) {
        makeQuiz(level);
        makeVariants(level);
    }

    /**
     * Create quiz from given data
     * @param numbers Equation numbers
     * @param variants Answer variants
     * @param answer Answer
     * @return Quiz
     */
    public static Quiz buildQuiz(List<Integer> numbers, List<Integer> variants, int answer) {
        if(numbers == null || variants == null) {
            throw new IllegalArgumentException("Not null arguments expected");
        }

        int sum = numbers.stream().collect(Collectors.summingInt(it -> it));
        if(sum != answer) {
            throw new IllegalArgumentException("Sum of numbers does not equal to answer");
        }

        boolean hasAnswer = variants.stream().anyMatch(it -> it == answer);
        if(!hasAnswer) {
            throw new IllegalArgumentException("Variants does not contain answer");
        }

        Quiz quiz = new Quiz();
        quiz.variants = variants;
        quiz.numbers = numbers;
        quiz.answer = answer;
        return quiz;
    }


    /**
     * Check whether answer is correct
     * @param answer Answer letter
     * @return Result
     */
    public boolean isCorrect(String answer) {
        if(answer.length() != 1) {
            return false;
        }

        try {
            return getVariant(answer) == this.answer;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Make quiz string
     * @return String
     */
    public String getQuizString() {
        StringBuilder quiz = new StringBuilder();

        for(int i = 0; i < numbers.size(); i++) {
            int num = numbers.get(i);

            if(i != 0 && num > 0) {
                quiz.append(" + ");
            }
            if(num < 0) {
                if(i != 0) {
                    quiz.append(" - ");
                } else {
                    quiz.append("-");
                }
            }

            quiz.append(Math.abs(num));
        }

        quiz.append(" = ?");

        return quiz.toString();
    }

    /**
     * Make quiz variants list
     * @return Variants
     */
    public List<QuizVariant> getQuizVariants() {
        List<QuizVariant> quizVariants = new ArrayList<>();

        for(int i = 0; i < variants.size(); i++) {
            QuizVariant variant = new QuizVariant(String.valueOf((char)('A' + i)), variants.get(i));
            quizVariants.add(variant);
        }

        return quizVariants;
    }


    /**
     * Make random quiz
     * @param level Difficulty level
     */
    private void makeQuiz(Level level) {
        answer = 0;

        numbers = new ArrayList<>();
        for(int i = 0; i < level.getNumbersCount(); i++) {
            int randomNumber = getRandomInt(level.getMinValue(), level.getMaxValue());
            int sign = random.nextInt(2) == 0 ? - 1 : 1;
            int nextNumber = randomNumber * sign;
            numbers.add(nextNumber);
            answer += nextNumber;
        }
    }

    /**
     * Make variants for quiz
     * @param level Difficulty level
     */
    private void makeVariants(Level level) {
        if(numbers == null) {
            return;
        }

        variants = new ArrayList<>();

        variants.add(answer);

        for(int i = 0; i < level.getVariantsCount() - 1; i++) {
            int nextVariant, variantsTried = 0;
            int variantLimit = level.getDeviation() / 2;
            do {
                nextVariant = answer + getRandomInt(-variantLimit, variantLimit);
                variantsTried++;
            } while(variants.indexOf(nextVariant) != -1 && variantsTried < 100);

            variants.add(nextVariant);
        }

        Collections.shuffle(variants, random);
    }

    /**
     * Get variant value by name
     * @param name Single uppercase latin letter
     * @return Variant value
     */
    private int getVariant(String name) throws IllegalArgumentException {
        int index = name.charAt(0) - 'A';

        if(index < 0 || index >= variants.size()) {
            throw new IllegalArgumentException("Wrong variant name: " + name);
        }

        return variants.get(index);
    }

    /**
     * Get random integer in bounds of min inclusive and max exclusive
     * If min is greater than max, swap bounds
     *
     * @param min Less inclusive bound
     * @param max Greater exclusive bound
     * @return Random integer
     */
    private static int getRandomInt(int min, int max) {
        if(min == max) {
            return min;
        }

        if(max < min) {
            int t = max;
            max = min;
            min = t;
        }

        return random.nextInt() % (max - min) + min;
    }

    ///////////////////////////////////////////////////////////////////////////

    public int getAnswer() {
        return answer;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public List<Integer> getVariants() {
        return variants;
    }
}
