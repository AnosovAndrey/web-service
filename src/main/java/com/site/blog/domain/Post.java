package com.site.blog.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message too long (more than 2Kb)")
    private String text;
    @Length(max = 255, message = "Message too long (more than 255)")
    private String title;

    private String output;
    private String filename;
    private String tag;

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name="compile_version")
    private Long compileVersion;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post(){
    }

    public Post(String text, String title, User user) {
        this.author = user;
        this.text = text;
        this.title = title;
    }

    public String getAuthorName(){
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getCompileVersion() {
        return compileVersion;
    }

    public void setCompileVersion(Long compileVersion) {
        this.compileVersion = compileVersion;
    }

    public void changeCompileVersion(){
        this.compileVersion = ++this.compileVersion;
    }
}
