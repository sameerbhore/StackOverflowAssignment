package demo.stackoverflow.controller;

import demo.stackoverflow.controller.requestmodel.QuestionRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Question;
import demo.stackoverflow.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionsController {

    private QuestionService questionService;

    @Autowired
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllQuestions() {
        Collection<Question> questions = questionService.getAllQuestions();
        if(questions == null || questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Set<QuestionRequest> questionRequests = questions.stream()
                                                .map(QuestionRequest::convert)
                                                .collect(Collectors.toSet());
        return new ResponseEntity(questionRequests, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createQuestion(@RequestBody @Valid QuestionRequest question) {
        questionService.save(question.toQuestion());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{questionid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateQuestion(@RequestBody @Valid QuestionRequest question,
                                            @PathVariable("questionid") String questionid) throws NotFoundException {
        questionService.updateQuestion(questionid, question);
        return new ResponseEntity(HttpStatus.OK);
    }
}
