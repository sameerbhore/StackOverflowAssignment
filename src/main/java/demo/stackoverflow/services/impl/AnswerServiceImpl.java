package demo.stackoverflow.services.impl;

import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Answer;
import demo.stackoverflow.persistance.AnswerDAO;
import demo.stackoverflow.persistance.QuestionDAO;
import demo.stackoverflow.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AnswerServiceImpl implements AnswerService {
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;

    @Autowired
    public AnswerServiceImpl(QuestionDAO questionDAO, AnswerDAO answerDAO) {
        this.questionDAO = questionDAO;
        this.answerDAO = answerDAO;
    }

    @Override
    public Collection<Answer> getAllAnswers(String questionId) throws NotFoundException {
        return answerDAO.getAllAnswers(questionId);
    }
}
