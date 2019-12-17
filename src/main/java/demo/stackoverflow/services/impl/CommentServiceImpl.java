package demo.stackoverflow.services.impl;

import demo.stackoverflow.controller.requestmodel.CommentRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Comment;
import demo.stackoverflow.persistance.CommentDAO;
import demo.stackoverflow.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDAO commentDAO;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public Collection<Comment> getAllComments() {
        Collection<Comment> list = commentDAO.getAllComments();
        return list;
    }

    @Override
    public void save(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public void updateComment(String commentId, CommentRequest comment) throws NotFoundException {
        commentDAO.updateComments(commentId, comment);

    }
}
