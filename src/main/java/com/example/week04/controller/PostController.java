package com.example.week04.controller;

import com.example.week04.dto.PostRequestDto;
import com.example.week04.dto.ResponseDto;
import com.example.week04.security.UserDetailsImpl;
import com.example.week04.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/api/auth/post")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        System.out.println(userDetailsImpl.getUsername());
        return postService.create(requestDto, userDetailsImpl);
    }

    @GetMapping("/api/post")
    public ResponseDto<?> getPosts() {
        return postService.findAll();
    }

    @GetMapping("/api/post/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.findOne(id);
    }

    @PutMapping("/api/auth/post/{id}")
    public ResponseDto<?> update(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.update(id, requestDto, userDetailsImpl);
    }

    @DeleteMapping("/api/auth/post/{id}")
    public ResponseDto<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.delete(id, userDetailsImpl);
    }
}
