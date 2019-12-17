package demo.stackoverflow.persistance.impl;

import demo.stackoverflow.controller.requestmodel.QuestionRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Question;
import demo.stackoverflow.persistance.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class QuestionsDAOImpl implements QuestionDAO {
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final class QuestionColumns {
        static final String QUESTION_ID = "question_id";
        static final String QUESTION = "question";
        static final String ANSWER_COUNT = "answers_count";
        static final String CREATED_BY = "created_by";
        static final String CREATED_AT = "created_at";
    }

    private static final String QUESTION_ID = "question_id";
    private static final String QUESTION = "question";
    private static final String ANSWER_COUNT = "answers_count";
    private static final String CREATED_BY = "created_by";
    private static final String CREATED_AT = "created_at";

    private final String INSERT_QUESTION = String.format("INSERT INTO stackoverflow.questions (%s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s)",
            QuestionColumns.QUESTION_ID, QuestionColumns.QUESTION, QuestionColumns.ANSWER_COUNT,
            QuestionColumns.CREATED_BY, QuestionColumns.CREATED_AT,
            QUESTION_ID, QUESTION, ANSWER_COUNT, CREATED_BY, CREATED_AT);

    private static final String SELECT_QUESTIONS = String.format("SELECT %s, %s, %s, %s, %s FROM stackoverflow.questions order by %s DESC",
            QuestionColumns.QUESTION_ID, QuestionColumns.QUESTION, QuestionColumns.ANSWER_COUNT,
            QuestionColumns.CREATED_BY, QuestionColumns.CREATED_AT, QuestionColumns.CREATED_AT);

    private static final String EXIST_QUESTION = String.format("SELECT EXISTS (SELECT 1 FROM stackoverflow.questions WHERE %s = :%s)",
            QuestionColumns.QUESTION_ID, QUESTION_ID);

    private static final String UPDATE_QUESTION = String.format("UPDATE stackoverflow.questions SET %s = :%s WHERE %s = :%s",
            QuestionColumns.QUESTION, QUESTION, QuestionColumns.QUESTION_ID, QUESTION_ID);


    @Autowired
    public QuestionsDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Question question) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(QUESTION_ID, question.getId())
                .addValue(QUESTION, question.getQuestion())
                .addValue(ANSWER_COUNT, question.getAnswers())
                .addValue(CREATED_BY, question.getCreated_by())
                .addValue(CREATED_AT, question.getCreated_at());
        jdbcTemplate.update(INSERT_QUESTION, mapSqlParameterSource);
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return jdbcTemplate.query(SELECT_QUESTIONS, (resultSet, i) ->
                mapRowToQuestion(resultSet));
    }

    private Question mapRowToQuestion(ResultSet resultSet) throws SQLException {
        return new Question(resultSet.getString(QuestionColumns.QUESTION_ID),
                resultSet.getString(QuestionColumns.QUESTION),
                resultSet.getString(QuestionColumns.CREATED_BY),
                resultSet.getLong(QuestionColumns.CREATED_AT),
                resultSet.getInt(QuestionColumns.ANSWER_COUNT)
        );
    }


    @Override
    public void updateQuestions(String questionId, QuestionRequest question) throws NotFoundException {
        checkIfExists(questionId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(QUESTION, question.getQuestion());
        mapSqlParameterSource.addValue(QUESTION_ID, questionId);
        jdbcTemplate.update(UPDATE_QUESTION, mapSqlParameterSource);
    }

    @Override
    public void checkIfExists(String questionId) throws NotFoundException {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(QUESTION_ID, questionId);
        Boolean exist = jdbcTemplate.queryForObject(EXIST_QUESTION, mapSqlParameterSource, Boolean.class);
        if(!exist){
            throw new NotFoundException(String.format("Question With %s not found", questionId));
        }
    }
}
