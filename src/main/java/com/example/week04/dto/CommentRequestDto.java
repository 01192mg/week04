package com.example.week04.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String content;
}
