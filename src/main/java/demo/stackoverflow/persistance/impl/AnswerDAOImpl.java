package demo.stackoverflow.persistance.impl;

import demo.stackoverflow.model.Answer;
import demo.stackoverflow.persistance.AnswerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class AnswerDAOImpl implements AnswerDAO {
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public AnswerDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class AnswerColumns {

        static final String ANSWER_ID = "answer_id";
        static final String QUESTION_ID = "question_id";
        static final String ANSWER = "answer";
        static final String COMMENT_COUNT = "comment_count";
        static final String CREATED_BY = "created_by";
        static final String CREATED_AT = "created_at";
    }
    private static final String ANSWER_ID = "answer_id";
    private static final String QUESTION_ID = "question_id";
    private static final String ANSWER = "answer";
    private static final String COMMENT_COUNT = "comment_count";
    private static final String CREATED_BY = "created_by";
    private static final String CREATED_AT = "created_at";

    private static final String SELECT_ANSWER = String.format("SELECT %s, %s, %s, %s, %s, %s from stackoverflow.answer where %s = :%s",
            AnswerColumns.ANSWER_ID, AnswerColumns.QUESTION_ID, AnswerColumns.ANSWER, AnswerColumns.CREATED_BY,
            AnswerColumns.CREATED_AT, AnswerColumns.COMMENT_COUNT, AnswerColumns.QUESTION_ID, QUESTION_ID);

    @Override
    public Collection<Answer> getAllAnswers(String questionId) {
        return jdbcTemplate.query(SELECT_ANSWER, (resultSet, i) ->
                mapRowToAnswer(resultSet));
    }
    private Answer mapRowToAnswer(ResultSet resultSet) throws SQLException {
        return new Answer(resultSet.getString(AnswerColumns.ANSWER_ID),
                resultSet.getString(AnswerColumns.QUESTION_ID),
                resultSet.getString(AnswerColumns.ANSWER),
                resultSet.getString(AnswerColumns.CREATED_BY),
                resultSet.getLong(AnswerColumns.CREATED_AT),
                resultSet.getInt(AnswerColumns.COMMENT_COUNT)
        );
    }
}
