package com.site.blog.repos;

import com.site.blog.domain.Comment;
import com.site.blog.domain.Post;
import com.site.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CommentRepo extends CrudRepository<Comment, Long> {

    @Query("from Comment m where m.author = :author")
    Page<Comment> findByUser(Pageable pageable, @Param("author") User author);

    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findByAuthor(User user, Pageable pageable);

    @Query("from Comment m where m.post = :post")
    Page<Comment> findByPost(Pageable pageable, @Param("post") Post post);

}
