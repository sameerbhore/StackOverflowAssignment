package demo.stackoverflow.model;

import java.time.Instant;
import java.util.UUID;

public class Answer {
    private String questionid;
    private String id;
    private String answer;
    private String created_by;
    private long created_at;
    private int comments;

    public Answer(String questionId, String created_by, String answer) {
        this.id = UUID.randomUUID().toString();
        this.questionid = questionId;
        this.answer = answer;
        this.created_by = created_by;
        this.created_at = Instant.now().toEpochMilli();
        this.comments = 0;
    }

    public Answer(String questionid, String id, String answer, String created_by, long created_at, int comments) {
        this.questionid = questionid;
        this.id = id;
        this.answer = answer;
        this.created_by = created_by;
        this.created_at = created_at;
        this.comments = comments;
    }

    public String getQuestionid() {
        return questionid;
    }

    public String getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCreated_by() {
        return created_by;
    }

    public long getCreated_at() {
        return created_at;
    }

    public int getComments() {
        return comments;
    }
}
