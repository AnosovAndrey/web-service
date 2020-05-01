package com.site.blog.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message too long (more than 2Kb)")
    private String text;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String filename;

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User receiver;


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Message() {
    }

    public Message(String text, User receiver, User user) {
        this.author = user;
        this.receiver = receiver;
        this.text = text;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public String getReceiverName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
