package ru.practicum.ewm.dto.comment.patcher;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.comment.PatchCommentDto;
import ru.practicum.ewm.model.Comment;

import java.time.LocalDateTime;

@Component
public class CommentPatcher {
    public Comment userPatch(Comment comment, PatchCommentDto patch) {
        Comment.CommentBuilder newComment = Comment.builder();
        newComment.id(comment.getId());
        if (patch.getText() != null || patch.getTitle() != null)
            newComment.createdOn(LocalDateTime.now());
        else newComment.createdOn(comment.getCreatedOn());
        newComment.event(comment.getEvent());
        newComment.author(comment.getAuthor());
        newComment.title(patch.getTitle() == null ? comment.getTitle() : patch.getTitle());
        newComment.text(patch.getText() == null ? comment.getText() : patch.getText());

        return newComment.build();
    }
}
