package demo.stackoverflow.controller.requestmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import demo.stackoverflow.model.Question;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionRequest {
    @JsonIgnore
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private String id;
    @NotNull @NotEmpty
    private String question;
    private String createdBy;
    private String createdAt;
    private int answers;

    public QuestionRequest(String id, @NotNull @NotEmpty String question, String createdBy, String created_at, int answers) {
        this.id = id;
        this.question = question;
        this.createdBy = createdBy;
        this.createdAt = created_at;
        this.answers = answers;
    }

    public QuestionRequest() {
    }

    @JsonSetter
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getAnswers() {
        return answers;
    }

    public static QuestionRequest convert(Question question) {
        QuestionRequest questionRequest = new QuestionRequest(
                question.getId(),question.getQuestion(),
                question.getCreated_by(),
                sdf.format(new Date(question.getCreated_at())),
                question.getAnswers()
        );
        return questionRequest;
    }

    public Question toQuestion() {
        Question question= new Question(this.question, SecurityContextHolder.getContext().getAuthentication().getName());
        return question;
    }
}
