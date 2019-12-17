package demo.stackoverflow.persistance;

import demo.stackoverflow.controller.requestmodel.CommentRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Comment;

import java.util.Collection;

public interface CommentDAO {
    void save(Comment comment);
    Collection<Comment> getAllComments();
    void updateComments(String commentId, CommentRequest comment) throws NotFoundException;
}
