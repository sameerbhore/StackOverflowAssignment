package demo.stackoverflow.persistance;

import demo.stackoverflow.controller.requestmodel.QuestionRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Question;

import java.util.Collection;

public interface QuestionDAO {
    void save(Question question);
    Collection<Question> getAllQuestions();
    void updateQuestions(String questionId, QuestionRequest question) throws NotFoundException;
    void checkIfExists(String questionId) throws NotFoundException;
}
