package com.site.blog.controller;


import com.site.blog.domain.Post;
import com.site.blog.repos.PostRepo;
import com.site.blog.service.CompileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.stream.Collectors;

@Controller
public class CompileController {
    @Autowired
    private CompileService compileService;
    @Autowired
    private PostRepo postRepo;

    @PostMapping("/compile/post/{post}/{compiler}")
    public ResponseEntity<String> compileCode(
            @PathVariable Post post,
            @PathVariable String compiler,
            @RequestBody(required = false) String input,
            Errors errors
    ){
        if (errors.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(errors.getAllErrors()
                            .stream().map(x -> x.getDefaultMessage())
                            .collect(Collectors.joining(",")));
        }

        compileService.compile(post, compiler, input);
        return ResponseEntity.ok("Start compile...");
    }

    @PostMapping("/api/search")
    public ResponseEntity<String> getSearchResultViaAjax(
             @RequestBody String postId,
             Errors errors
    ) {
        //If error, just return a 400 bad request, along with the error message
        //System.out.println(postId);
        if (errors.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(errors.getAllErrors()
                            .stream().map(x -> x.getDefaultMessage())
                            .collect(Collectors.joining(",")));
        }

        Post originalPost = postRepo.findById(Long.valueOf(postId)).get();
        String output = originalPost.getOutput();
        //System.out.println(output);
        if (output != null && !output.isEmpty()) {
            output = "Compiler output: " + output;
        } else {
            output = "not found";
        }

        return ResponseEntity.ok(output);
    }

}
