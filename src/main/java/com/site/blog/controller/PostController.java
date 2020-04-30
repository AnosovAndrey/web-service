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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class PostController {
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


    @GetMapping("/posts")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> page = postService.postListByTitle(pageable, filter);

        model.addAttribute("page", page);
        model.addAttribute("url", "/posts");
        model.addAttribute("filter", filter);

        return "posts";
    }

    @PostMapping("/posts")
    public String addPost(
            @AuthenticationPrincipal User user,
            @Valid Post post,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        post.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("post", post);
        } else {

            saveFile(post, file);

            model.addAttribute("post", null);

            postRepo.save(post);
        }

        Iterable<Post> posts = postRepo.findAll();

        model.addAttribute("posts", posts);

        return "redirect:/posts";
    }

    private void saveFile(@Valid Post post, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            post.setFilename(resultFilename);
        }
    }

    private void saveCommentFile(@Valid Comment comment, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            comment.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-posts/{user}")
    public String userPosts(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Post post,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageble
    ) {
        //Page<Message> page = messageService.messageListForUser(pageble, currentUser, author);

        Page<Post> page = postRepo.findByAuthor(user, pageble);
        Set<Post> posts = user.getPosts();

        model.addAttribute("posts", page);
        model.addAttribute("post", post);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("url", "/user-posts/" + user.getId());
        model.addAttribute("page", page);

        return "userPosts";
    }

    @PostMapping("/user-posts/{user}")
    public String updatePosts(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Post post,
            @RequestParam("text") String text,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file,
            @RequestParam("tag") String tag
    ) throws IOException {
        if(post.getAuthor().equals(currentUser)){
            //if (message == null)
            if(!StringUtils.isEmpty(text)){
                post.setText(text);
            }
            if(!StringUtils.isEmpty(title)){
                post.setTitle(title);
            }
            if(!StringUtils.isEmpty(tag)){
                post.setTag(tag);
            }
            saveFile(post, file);
            post.setOutput(null);

            postRepo.save(post);
        }

        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/{post}")
    public String showPost(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Post post,
            Model model,
            //@RequestParam(required = false) User user,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageble
    ) {

        //Page<Post> page = postRepo.findByAuthor(user, pageble);
        //Set<Post> posts = user.getPosts();
        Page<Comment> comments = commentRepo.findByPost(pageble, post);

        model.addAttribute("comments", comments);
        model.addAttribute("post", post);
        //model.addAttribute("posts", page);
        model.addAttribute("isCurrentUser", currentUser.equals(post.getAuthor()));
        //model.addAttribute("userChannel", user);
        model.addAttribute("url", "/post/" + post.getId());
        model.addAttribute("page", comments);

        return "post";
    }

    @PostMapping("/post/{post}")
    public String leaveComment(
            @AuthenticationPrincipal User currentUser,
            @Valid Comment comment,
            BindingResult bindingResult,
            Model model,
            @PathVariable Post post,
            @RequestParam("text") String text,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
            if(!StringUtils.isEmpty(text)){
                comment.setText(text);
                comment.setAuthor(currentUser);
                comment.setPost(post);
            }

            saveCommentFile(comment, file);

            commentRepo.save(comment);


        return "redirect:/post/" + post.getId();
    }
}