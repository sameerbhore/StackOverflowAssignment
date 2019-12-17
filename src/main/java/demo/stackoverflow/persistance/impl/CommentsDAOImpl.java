package demo.stackoverflow.persistance.impl;

import demo.stackoverflow.controller.requestmodel.CommentRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Comment;
import demo.stackoverflow.persistance.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class CommentsDAOImpl implements CommentDAO {
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final class CommentColumns {
        static final String COMMENT_ID = "comment_id";
		private static final String CONTEXT_ID = "context_id";
		private static final String CONTEXT_TYPE = "context_type";
        static final String COMMENT = "comment";
        static final String COMMENT_COUNT = "comments_count";
        static final String CREATED_BY = "created_by";
        static final String CREATED_AT = "created_at";
    }

    private static final String COMMENT_ID = "comment_id";
	private static final String CONTEXT_ID = "context_id";
	private static final String CONTEXT_TYPE = "context_type";
    private static final String COMMENT = "comment";
    private static final String COMMENT_COUNT = "comments_count";
    private static final String CREATED_BY = "created_by";
    private static final String CREATED_AT = "created_at";

    private final String INSERT_COMMENT = String.format("INSERT INTO stackoverflow.comments (%s, %s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s, :%s)",
            CommentColumns.COMMENT_ID, CommentColumns.CONTEXT_ID, CommentColumns.CONTEXT_TYPE, CommentColumns.COMMENT, CommentColumns.COMMENT_COUNT,
            CommentColumns.CREATED_BY, CommentColumns.CREATED_AT,
            COMMENT_ID, CONTEXT_ID, CONTEXT_TYPE, COMMENT, COMMENT_COUNT, CREATED_BY, CREATED_AT);

    private static final String SELECT_COMMENTS = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM stackoverflow.comments order by %s DESC",
            CommentColumns.COMMENT_ID, CommentColumns.CONTEXT_ID, CommentColumns.CONTEXT_TYPE, CommentColumns.COMMENT, CommentColumns.COMMENT_COUNT,
            CommentColumns.CREATED_BY, CommentColumns.CREATED_AT, CommentColumns.CREATED_AT);

    private static final String EXIST_COMMENT = String.format("SELECT EXISTS (SELECT 1 FROM stackoverflow.comments WHERE %s = :%s)",
            CommentColumns.COMMENT_ID, COMMENT_ID);

    private static final String UPDATE_COMMENT = String.format("UPDATE stackoverflow.comments SET %s = :%s WHERE %s = :%s",
            CommentColumns.COMMENT, COMMENT, CommentColumns.COMMENT_ID, COMMENT_ID);


    @Autowired
    public CommentsDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Comment comment) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(COMMENT_ID, comment.getId())
				.addValue(CONTEXT_ID, comment.getComment())
				.addValue(COMMENT, comment.getComment())
                .addValue(COMMENT, comment.getComment())
                .addValue(COMMENT_COUNT, comment.getComments())
                .addValue(CREATED_BY, comment.getCreated_by())
                .addValue(CREATED_AT, comment.getCreated_at());
        jdbcTemplate.update(INSERT_COMMENT, mapSqlParameterSource);
    }

    @Override
    public Collection<Comment> getAllComments() {
        return jdbcTemplate.query(SELECT_COMMENTS, (resultSet, i) ->
                mapRowToComment(resultSet));
    }

    private Comment mapRowToComment(ResultSet resultSet) throws SQLException {
        return new Comment(resultSet.getString(CommentColumns.COMMENT_ID),
				resultSet.getString(CommentColumns.CONTEXT_ID),
				resultSet.getString(CommentColumns.CONTEXT_TYPE),
                resultSet.getString(CommentColumns.COMMENT),
                resultSet.getString(CommentColumns.CREATED_BY),
                resultSet.getLong(CommentColumns.CREATED_AT),
                resultSet.getInt(CommentColumns.COMMENT_COUNT)
        );
    }


    @Override
    public void updateComments(String commentId, CommentRequest comment) throws NotFoundException {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COMMENT_ID, commentId);
        Boolean exist = jdbcTemplate.queryForObject(EXIST_COMMENT, mapSqlParameterSource, Boolean.class);
        if(!exist){
            throw new NotFoundException(String.format("Comment With %s not found", commentId));
        }
        mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(COMMENT, comment.getComment())
                .addValue(COMMENT_ID, commentId);
        jdbcTemplate.update(UPDATE_COMMENT, mapSqlParameterSource);
    }
}
