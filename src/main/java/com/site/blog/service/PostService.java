package com.site.blog.service;

import com.site.blog.domain.Post;
import com.site.blog.domain.User;
import com.site.blog.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    public Page<Post> messageListForUser(Pageable pageable, User currentUser, User author) {
        return postRepo.findByUser(pageable, author);
    }

    public Page<Post> messageList(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()) {
            return postRepo.findByTag(filter, pageable);
        } else {
            return postRepo.findAll(pageable);
        }
    }
}

