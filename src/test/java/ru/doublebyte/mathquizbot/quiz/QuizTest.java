package ru.doublebyte.mathquizbot.quiz;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class QuizTest {

    @Test
    public void isCorrect() throws Exception {
        Quiz quiz = new Quiz(Level.Simple);

        for(QuizVariant variant : quiz.getQuizVariants()) {
            assertEquals(variant.getValue() == quiz.getAnswer(),
                    quiz.isCorrect(variant.getName()));
        }
    }

    @Test
    public void getQuizString() throws Exception {
        Quiz quiz = new Quiz(Level.Simple);
        String quizString = quiz.getQuizString();
        assertNotNull(quizString);
        assertTrue(quizString.contains("="));
        assertTrue(quizString.contains("?"));
    }

    @Test
    public void getQuizVariants() throws Exception {
        Level level = Level.Simple;
        Quiz quiz = new Quiz(level);

        List<QuizVariant> variants = quiz.getQuizVariants();
        assertEquals(level.getVariantsCount(), variants.size());

        boolean answerInVariants = false;
        for(QuizVariant variant : variants) {
            if(variant.getValue() == quiz.getAnswer()) {
                answerInVariants = true;
            }
        }
        assertTrue(answerInVariants);
    }

    @Test
    public void render() {
        Logger logger = LoggerFactory.getLogger(QuizTest.class);
        logger.info("Testing quiz output");

        Quiz simpleQuiz = new Quiz(Level.Simple);
        logger.info("Simple quiz");
        logger.info(simpleQuiz.getQuizString());
        simpleQuiz.getQuizVariants().forEach(it -> logger.info(it.toString()));

        Quiz mediumQuiz = new Quiz(Level.Medium);
        logger.info("Medium quiz");
        logger.info(mediumQuiz.getQuizString());
        mediumQuiz.getQuizVariants().forEach(it -> logger.info(it.toString()));

        Quiz hardQuiz = new Quiz(Level.Hard);
        logger.info("Hard quiz");
        logger.info(hardQuiz.getQuizString());
        hardQuiz.getQuizVariants().forEach(it -> logger.info(it.toString()));
    }

    @Test
    public void testBuildQuiz() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Integer> variants = Arrays.asList(5, 6, 7, 8);
        int answer = 6;

        try {
            Quiz quiz = Quiz.buildQuiz(numbers, variants, answer);
            assertNotNull(quiz);
            assertEquals(answer, quiz.getAnswer());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            Quiz.buildQuiz(null, variants, answer);
            fail("Must throw");
        } catch(IllegalArgumentException ignored) {

        } catch(Exception e) {
            fail(e.getMessage());
        }

        try {
            Quiz.buildQuiz(numbers, null, answer);
            fail("Must throw");
        } catch(IllegalArgumentException ignored) {

        } catch(Exception e) {
            fail(e.getMessage());
        }

        try {
            Quiz.buildQuiz(numbers, variants, 5);
            fail("Must throw");
        } catch(IllegalArgumentException ignored) {

        } catch(Exception e) {
            fail(e.getMessage());
        }

        try {
            List<Integer> wrongVariants = Arrays.asList(10, 20, 30, 40);
            Quiz.buildQuiz(numbers, wrongVariants, answer);
            fail("Must throw");
        } catch(IllegalArgumentException ignored) {

        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

}