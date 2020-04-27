package com.site.blog.controller;


import com.site.blog.domain.Post;
import com.site.blog.domain.User;
import com.site.blog.repos.PostRepo;
import com.site.blog.service.CompileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class CompileController {
    @Autowired
    private CompileService compileService;
    @Autowired
    private PostRepo postRepo;

    @PostMapping("/compile/post/{post}")
    public String compileCode(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Post post,
            @RequestParam(name = "input", required = false) String input,
            @RequestParam("compiler") String compiler
    ){
        compileService.compile(post, compiler, input);


        return "redirect:/post/" + post.getId();
    }

    @PostMapping("/api/search")
    public ResponseEntity<String> getSearchResultViaAjax(
             @RequestBody String postId,
             Errors errors
    ) {
        //If error, just return a 400 bad request, along with the error message
        System.out.println(postId);
        if (errors.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(errors.getAllErrors()
                            .stream().map(x -> x.getDefaultMessage())
                            .collect(Collectors.joining(",")));
        }

        Post originalPost = postRepo.findById(Long.valueOf(postId)).get();
        String output = originalPost.getOutput();
        if (output.isEmpty() || output == null) {
            output = "not found";
        } else {
            output = "Compiler output: " + output;
        }

        return ResponseEntity.ok(output);
    }

}