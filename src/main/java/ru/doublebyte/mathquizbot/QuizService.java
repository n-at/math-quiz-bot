package ru.doublebyte.mathquizbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.doublebyte.mathquizbot.quiz.Quiz;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Save/load quiz from database
 */
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private static final String insertQuery =
            "insert into quiz (id, numbers, variants, answer) values (?, ?, ?, ?)";
    private static final String selectQuery =
            "select * from quiz where id = ?";

    private JdbcTemplate jdbcTemplate;


    public QuizService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * Get quiz by it's id
     * @param id Quiz id
     * @return Quiz or null if not found
     */
    public Quiz getById(UUID id) {
        try {
            return jdbcTemplate.queryForObject(selectQuery, new Object[]{id.toString()}, (resultSet, i) -> {
                List<Integer> numbers = Arrays.asList(resultSet.getString("numbers").split(";"))
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                List<Integer> variants = Arrays.asList(resultSet.getString("variants").split(";"))
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                int answer = resultSet.getInt("answer");

                return Quiz.buildQuiz(numbers, variants, answer);
            });
        } catch(Exception e) {
            logger.error("Quiz load error", e);
            return null;
        }
    }

    /**
     * Save quiz in db
     * @param id Quiz id
     * @param quiz Quiz
     * @return true if saved successfully
     */
    public boolean save(UUID id, Quiz quiz) {
        if(id == null || quiz == null) {
            logger.warn("Incorrect quiz: {}", id);
            return false;
        }

        String numbers = quiz.getNumbers()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(";"));

        String variants = quiz.getVariants()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(";"));

        try {
            jdbcTemplate.update(insertQuery, id.toString(), numbers, variants, quiz.getAnswer());
        } catch(Exception e) {
            logger.error("Quiz save error", e);
        }

        return true;
    }

}
