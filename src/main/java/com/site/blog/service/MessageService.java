package com.site.blog.service;

import com.site.blog.domain.Message;
import com.site.blog.domain.User;
import com.site.blog.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    public Page<Message> messageListForUser(Pageable pageable, User currentUser, User author) {
        return messageRepo.findByUser(pageable, author);
    }

    public Page<Message> messageList(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()) {
            //return messageRepo.findByTag(filter, pageable);
            return messageRepo.findAll(pageable);
        } else {
            return messageRepo.findAll(pageable);
        }
    }
}
