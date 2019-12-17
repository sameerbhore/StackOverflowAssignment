package demo.stackoverflow.model;

import java.time.Instant;
import java.util.UUID;

public class Question {
    private String id;
    private String question;
    private String created_by;
    private long created_at;
    private int answers;

    public Question(String question, String created_by) {
        this.id = UUID.randomUUID().toString();
        this.question = question;
        this.created_by = created_by;
        this.created_at = Instant.now().toEpochMilli();
        this.answers = 0;
    }

    public Question(String id, String question, String created_by, long created_at, int answers) {
        this.id = id;
        this.question = question;
        this.created_by = created_by;
        this.created_at = created_at;
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCreated_by() {
        return created_by;
    }

    public long getCreated_at() {
        return created_at;
    }

    public int getAnswers() {
        return answers;
    }
}
