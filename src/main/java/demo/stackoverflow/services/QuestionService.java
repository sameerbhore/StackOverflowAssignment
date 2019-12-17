package demo.stackoverflow.services;

import demo.stackoverflow.controller.requestmodel.QuestionRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Question;

import java.util.Collection;

public interface QuestionService {
    Collection<Question> getAllQuestions();

    void save(Question question);

    void updateQuestion(String questionId, QuestionRequest question) throws NotFoundException;
}
