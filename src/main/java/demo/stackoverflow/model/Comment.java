package demo.stackoverflow.model;

import java.time.Instant;
import java.util.UUID;

public class Comment {
    private String id;
	private String contextId;
	private String contextType;
    private String comment;
    private String created_by;
    private long created_at;
    private int comments;

    public Comment(String comment, String contextId, String contextType, String created_by) {
        this.id = UUID.randomUUID().toString();
		this.contextId = contextId;
		this.contextType = contextType;
        this.comment = comment;
        this.created_by = created_by;
        this.created_at = Instant.now().toEpochMilli();
        this.comments = 0;
    }

    public Comment(String id, String contextId, String contextType, String comment, String created_by, long created_at, int comments) {
        this.id = id;
		this.contextId = contextId;
		this.contextType = contextType;
        this.comment = comment;
        this.created_by = created_by;
        this.created_at = created_at;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }
	
    public String getContextId() {
        return contextId;
    }
	
    public String getContextType() {
        return contextType;
    }
	
    public String getComment() {
        return comment;
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
