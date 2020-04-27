package com.site.blog.service;

import com.site.blog.domain.CompileExecutor;
import com.site.blog.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class CompileService {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TaskExecutor taskExecutor;

    public void compile(Post post, String compiler, String input) {
        CompileExecutor compileExecutor = applicationContext.getBean(CompileExecutor.class, post, compiler, input);
        taskExecutor.execute(compileExecutor);
    }
}
