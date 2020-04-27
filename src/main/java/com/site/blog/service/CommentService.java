package com.site.blog.service;

import com.site.blog.domain.Comment;
import com.site.blog.domain.Post;
import com.site.blog.domain.User;
import com.site.blog.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    public Page<Comment> messageListForUser(Pageable pageable, User currentUser, User author) {
        return commentRepo.findByUser(pageable, author);
    }

    public Page<Comment> messageList(Pageable pageable, String filter){
        return commentRepo.findAll(pageable);
    }

    public Page<Comment> messageListForPost(Pageable pageable, Post post){
        return commentRepo.findByPost(pageable, post);
    }
}