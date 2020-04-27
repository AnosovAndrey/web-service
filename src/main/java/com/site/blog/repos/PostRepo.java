package com.site.blog.repos;

import com.site.blog.domain.Post;
import com.site.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface PostRepo extends CrudRepository<Post, Long> {

    @Query("from Post m where m.author = :author")
    Page<Post> findByUser(Pageable pageable, @Param("author") User author);

    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTag(String tag, Pageable pageable);
    Page<Post> findByAuthor(User user, Pageable pageable);

}
