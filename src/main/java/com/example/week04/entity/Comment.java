package com.example.week04.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;


    public Comment(String content, String author, Post post) {
        this.post = post;
        this.content = content;
        this.author = author;
        post.getComments().add(this);
    }

    public void update(Post post, String content) {
        this.post = post;
        this.content = content;
    }
}
