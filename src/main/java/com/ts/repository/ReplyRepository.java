package com.ts.repository;

import com.ts.entity.Comment;
import com.ts.entity.Reply;
import com.ts.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE r.comment.id = :commentId")
    List<Reply> getByCommentId(Long commentId);

    List<Reply> getRepliesByComment(Comment comment);
}
