package com.site.blog.controller;

import com.site.blog.domain.Comment;
import com.site.blog.domain.Post;
import com.site.blog.domain.User;
import com.site.blog.repos.CommentRepo;
import com.site.blog.repos.PostRepo;
import com.site.blog.service.CommentService;
import com.site.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.util.Iterator;

@Controller
public class AdminController {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteComment/{comment}")
    public String deleteComment(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Comment comment
    ) {
        Long postId = comment.getPost().getId();
        if (comment.getFilename() != null) {
            deleteFile(comment.getFilename());
        }

        commentRepo.delete(comment);

        return "redirect:/post/" + postId;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deletePost/{post}")
    public String deletePost(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Post post,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageble
    ) {
        if (post.getFilename() != null) {
            deleteFile(post.getFilename());
        }

        commentRepo.findByPost(pageble, post).stream()
                .forEach(comment -> {
                    if (comment.getFilename() != null) {
                        deleteFile(comment.getFilename());
                    }
                    commentRepo.delete(comment);
                });
        /*Iterable<Comment> comments = commentRepo.findByPost(pageble, post);
        for (Iterator<Comment> it = comments.iterator(); it.hasNext(); ) {
            Comment comment = it.next();
            if (comment.getFilename() != null) {
                deleteFile(comment.getFilename());
            }
            commentRepo.delete(comment);
        }*/

        postRepo.delete(post);

        return "redirect:/posts";
    }

    private void deleteFile(String filename) {
        File file = new File(uploadPath + "/" + filename);
        file.delete();
    }

}
