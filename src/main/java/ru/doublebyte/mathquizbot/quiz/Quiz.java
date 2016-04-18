package ru.doublebyte.mathquizbot.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Quiz {

    private static final Random random = new Random();

    private Level level;
    private List<Integer> numbers;
    private List<Integer> variants;
    private int answer;


    public Quiz(Level level) {
        this.level = level;
        makeQuiz();
        makeVariants();
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
     */
    private void makeQuiz() {
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
     */
    private void makeVariants() {
        if(numbers == null) {
            return;
        }

        variants = new ArrayList<>();

        variants.add(answer);

        for(int i = 0; i < level.getVariantsCount() - 1; i++) {
            int nextVariant;
            do {
                nextVariant = getRandomInt((int)Math.round(answer - answer * 0.2),
                        (int)Math.round(answer + answer * 0.2));
            } while(variants.indexOf(nextVariant) != -1);

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
        if(max < min) {
            int t = max;
            max = min;
            min = t;
        }

        return random.nextInt() % (max - min) + min;
    }

    ///////////////////////////////////////////////////////////////////////////

    public Level getLevel() {
        return level;
    }

    public int getAnswer() {
        return answer;
    }
}
