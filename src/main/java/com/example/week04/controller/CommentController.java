package com.example.week04.controller;

import com.example.week04.dto.CommentRequestDto;
import com.example.week04.dto.ResponseDto;
import com.example.week04.security.UserDetailsImpl;
import com.example.week04.service.CommentService;
import com.example.week04.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/api/comment/{id}")
    public ResponseDto<?> getComments(@PathVariable Long id) {
        return postService.findComments(id);
    }

    @PostMapping("/api/auth/comment")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.create(requestDto, userDetails);
    }

    @PutMapping("/api/auth/comment/{id}")
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/api/auth/comment/{id}")
    public ResponseDto<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(id, userDetails);
    }
}
