package com.example.week04.controller;

import com.example.week04.dto.PostRequestDto;
import com.example.week04.dto.ResponseDto;
import com.example.week04.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 제목, 작성내용 입력
    // access token, valid token 일 때만 가능
    @PostMapping("/api/posts")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto) {
        return postService.create(requestDto);
    }

    // 제목, 작성자명, 작성 날짜 조회
    // 작성 날짜 기준 내림차순
    // access token 없어도 조회 가능
    @GetMapping("/api/posts")
    public ResponseDto<?> getPosts() {
        return postService.findAll();
    }

    // 제목, 작성자명, 작성 날짜, 작성 내용 조회
    // access token 없어도 조회 가능
    @GetMapping("/api/posts/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.findOne(id);
    }

    // 제목, 작성자명, 작성 내용
    // access token, valid token && 해당 유저일 때만 가능
    @PutMapping("/api/posts/{id}")
    public ResponseDto<?> update(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    // 글과 댓글이 함께 삭제
    // access token, valid token && 해당 유저일 때만 가능
    @DeleteMapping("/api/posts/{id}")
    public ResponseDto<?> delete(@PathVariable Long id) {
        return postService.delete(id);
    }
}
