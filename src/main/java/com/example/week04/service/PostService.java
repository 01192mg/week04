package com.example.week04.service;

import com.example.week04.dto.CommentResponseDto;
import com.example.week04.dto.PostResponseDto;
import com.example.week04.entity.Post;
import com.example.week04.dto.PostRequestDto;
import com.example.week04.dto.ResponseDto;
import com.example.week04.repository.PostRepository;
import com.example.week04.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;


    public ResponseDto<?> create(PostRequestDto requestDto, UserDetailsImpl userDetailsImpl) {
        Post post = new Post(requestDto, userDetailsImpl.getUser());
        Post savedPost = postRepository.save(post);
        return ResponseDto.success(new PostResponseDto(savedPost, null));
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> findAll() {
        return ResponseDto.success(postRepository.findAllByOrderByCreatedAtDesc());
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> findOne(Long id) {
        Optional<Post> findPost = postRepository.findById(id);
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        Post post = findPost.get();
        List<CommentResponseDto> commentResponseDtoList = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        return ResponseDto.success(new PostResponseDto(post , commentResponseDtoList));
    }

    public ResponseDto<?> update(Long id, PostRequestDto requestDto, UserDetailsImpl userDetailsImpl) {
        Optional<Post> findPost = postRepository.findById(id);
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        Post post = findPost.get();
        if (!post.getMember().getId().equals(userDetailsImpl.getUser().getId())) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        post.update(requestDto);
        return ResponseDto.success(post);
    }


    public ResponseDto<?> delete(Long id, UserDetailsImpl userDetailsImpl) {
        Optional<Post> findPost = postRepository.findById(id);
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }
        Post post = findPost.get();
        System.out.println("principal = " + userDetailsImpl.getUser().getId());
        System.out.println("post = " + post.getMember().getId());
        if (!post.getMember().getId().equals(userDetailsImpl.getUser().getId())) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        postRepository.delete(post);
        return ResponseDto.success("delete success");
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> findComments(Long id) {
        Optional<Post> findPost = postRepository.findById(id);
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }
        Post post = findPost.get();
        return ResponseDto.success(post.getComments());
    }
}
