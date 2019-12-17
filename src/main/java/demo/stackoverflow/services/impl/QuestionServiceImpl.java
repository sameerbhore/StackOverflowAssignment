package demo.stackoverflow.services.impl;

import demo.stackoverflow.controller.requestmodel.QuestionRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Question;
import demo.stackoverflow.persistance.QuestionDAO;
import demo.stackoverflow.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDAO questionDAO;

    @Autowired
    public QuestionServiceImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public Collection<Question> getAllQuestions() {
        Collection<Question> list = questionDAO.getAllQuestions();
        return list;
    }

    @Override
    public void save(Question question) {
        questionDAO.save(question);
    }

    @Override
    public void updateQuestion(String questionId, QuestionRequest question) throws NotFoundException {
        questionDAO.updateQuestions(questionId, question);

    }
}
