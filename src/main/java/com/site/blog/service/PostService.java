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

    public Page<Post> postListForUser(Pageable pageable, User currentUser, User author) {
        return postRepo.findByUser(pageable, author);
    }

    public Page<Post> postListByTag(Pageable pageable, String tag) {
        return postRepo.findByTag(tag, pageable);
    }

    public Page<Post> postListByTitle(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()) {
            return postRepo.findByTitle(filter, pageable);
        } else {
            return postRepo.findAll(pageable);
        }
    }

    public Page<Post> postsWithTag(Pageable pageable, String tag){
        if (tag != null && !tag.isEmpty()) {
            return postRepo.findByTag(tag, pageable);
        } else {
            return postRepo.findAll(pageable);
        }
    }
}

