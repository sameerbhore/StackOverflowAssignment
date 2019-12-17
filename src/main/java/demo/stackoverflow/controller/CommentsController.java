package demo.stackoverflow.controller;

import demo.stackoverflow.controller.requestmodel.CommentRequest;
import demo.stackoverflow.exception.NotFoundException;
import demo.stackoverflow.model.Comment;
import demo.stackoverflow.services.CommentService;
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
@RequestMapping("/api/v1/answers/{answerid}/comments")
public class CommentsController {

    private CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllComments() {
        Collection<Comment> comments = commentService.getAllComments();
        if(comments == null || comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Set<CommentRequest> commentRequests = comments.stream()
                                                .map(CommentRequest::convert)
                                                .collect(Collectors.toSet());
        return new ResponseEntity(commentRequests, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentRequest comment) {
        commentService.save(comment.toComment("123", "ANSWER"));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{commentid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateComment(@RequestBody @Valid CommentRequest comment,
                                            @PathVariable("commentid") String commentid) throws NotFoundException {
        commentService.updateComment(commentid, comment);
        return new ResponseEntity(HttpStatus.OK);
    }
}
