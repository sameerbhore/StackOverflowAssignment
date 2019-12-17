package demo.stackoverflow.persistance;

import demo.stackoverflow.model.Answer;

import java.util.Collection;

public interface AnswerDAO {
    Collection<Answer> getAllAnswers(String questionId);
}
