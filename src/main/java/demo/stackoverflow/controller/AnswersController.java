package demo.stackoverflow.controller;

import demo.stackoverflow.controller.requestmodel.AnswerRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Answer;
import demo.stackoverflow.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/answers")
public class AnswersController {

    private AnswerService answerService;

    @Autowired
    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAnswers(@PathVariable("questionId") String questionId) throws NotFoundException {
        Collection<Answer> answers = answerService.getAllAnswers(questionId);
        if(answers == null || answers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Set<AnswerRequest> questionRequests = answers.stream()
                .map(AnswerRequest::convert)
                .collect(Collectors.toSet());
        return new ResponseEntity(questionRequests, HttpStatus.OK);
    }

}
