package ru.doublebyte.mathquizbot.quiz;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void getLevel() throws Exception {
        Quiz quiz = new Quiz(Level.Medium);
        assertEquals(Level.Medium, quiz.getLevel());
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
        logger.info("Meduim quiz");
        logger.info(mediumQuiz.getQuizString());
        mediumQuiz.getQuizVariants().forEach(it -> logger.info(it.toString()));

        Quiz hardQuiz = new Quiz(Level.Hard);
        logger.info("Hard quiz");
        logger.info(hardQuiz.getQuizString());
        hardQuiz.getQuizVariants().forEach(it -> logger.info(it.toString()));
    }

}