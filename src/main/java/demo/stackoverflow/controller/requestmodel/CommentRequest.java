package demo.stackoverflow.controller.requestmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import demo.stackoverflow.model.Comment;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentRequest {
    @JsonIgnore
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private String id;
	@NotNull @NotEmpty
    private String context_id;
	@NotNull @NotEmpty
    private String context_type;
    @NotNull @NotEmpty
    private String comment;
    private String createdBy;
    private String createdAt;
	private int comments;

    public CommentRequest(String id, @NotNull @NotEmpty String context_id, @NotNull @NotEmpty String context_type, @NotNull @NotEmpty String comment, String createdBy, String created_at, int comments) {
        this.id = id;
		this.context_id = context_id;
		this.context_type = context_type;
        this.comment = comment;
        this.createdBy = createdBy;
        this.createdAt = created_at;
        this.comments = comments;
    }

    public CommentRequest() {
    }

    @JsonSetter
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }
	
    public String getComment() {
        return comment;
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

    public static CommentRequest convert(Comment comment) {
        CommentRequest commentRequest = new CommentRequest(
                comment.getId(), comment.getContextId(), comment.getContextType(), comment.getComment(),
                comment.getCreated_by(),
                sdf.format(new Date(comment.getCreated_at())),
                comment.getComments()
        );
        return commentRequest;
    }

    public Comment toComment(String contextId, String contextType) {
        Comment comment= new Comment(this.comment,contextId,contextType, SecurityContextHolder.getContext().getAuthentication().getName());
        return comment;
    }
}
