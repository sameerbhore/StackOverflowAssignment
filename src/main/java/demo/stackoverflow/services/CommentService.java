package demo.stackoverflow.services;

import demo.stackoverflow.controller.requestmodel.CommentRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Comment;

import java.util.Collection;

public interface CommentService {
    Collection<Comment> getAllComments();

    void save(Comment comment);

    void updateComment(String commentId, CommentRequest comment) throws NotFoundException;
}
