package demo.stackoverflow.services;

import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Answer;

import java.util.Collection;

public interface AnswerService {
    Collection<Answer> getAllAnswers(String questionId) throws NotFoundException;
}
