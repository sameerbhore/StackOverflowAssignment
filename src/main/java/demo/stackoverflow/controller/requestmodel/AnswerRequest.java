package demo.stackoverflow.controller.requestmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import demo.stackoverflow.model.Answer;
import demo.stackoverflow.model.Question;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerRequest {
    @JsonIgnore
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private String id;
    @NotNull @NotEmpty	
    private String answer;
    private String createdBy;
    private String createdAt;
    private int comments;

    public AnswerRequest(String id, @NotNull @NotEmpty String answer, String createdBy, String created_at, int comments) {
        this.id = id;
        this.answer = answer;
        this.createdBy = createdBy;
        this.createdAt = created_at;
        this.comments = comments;
    }

    public AnswerRequest() {
    }

    @JsonSetter
    public void setAnswer(String answer) {
        this.answer = answer;
    }
	
    public String getId() {
        return id;
    }
	
    public String getAnswer() {
        return answer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getComments() {
        return comments;
    }

    public static AnswerRequest convert(Answer answer) {
        AnswerRequest answerRequest = new AnswerRequest(
                answer.getId(), answer.getAnswer(),
                answer.getCreated_by(),
                sdf.format(new Date(answer.getCreated_at())),
                answer.getComments()
        );
        return answerRequest;
    }

    public Answer toAnswer(String questionId) {
        Answer answer= new Answer(this.answer, questionId, SecurityContextHolder.getContext().getAuthentication().getName());
        return answer;
    }
}
