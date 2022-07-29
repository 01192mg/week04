package com.example.week04.service;

import com.example.week04.entity.Post;
import com.example.week04.dto.PostRequestDto;
import com.example.week04.dto.ResponseDto;
import com.example.week04.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;


    public ResponseDto<?> create(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        return ResponseDto.success(postRepository.save(post));
    }

    public ResponseDto<?> findAll() {
        return ResponseDto.success(postRepository.findAllByOrderByCreatedAtDesc());
    }


    public ResponseDto<?> findOne(Long id) {
        Optional<Post> findPost = postRepository.findById(id);
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        return ResponseDto.success(findPost.get());
    }

    public ResponseDto<?> update(Long id, PostRequestDto requestDto) {
        Optional<Post> findPost = postRepository.findById(id);
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        Post post = findPost.get();
        post.update(requestDto);
        return ResponseDto.success(post);
    }


    public ResponseDto<?> delete(Long id) {
        Optional<Post> findPost = postRepository.findById(id);
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }
        Post post = findPost.get();
        postRepository.delete(post);
        return ResponseDto.success(true);
    }
}
