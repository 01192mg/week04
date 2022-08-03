package com.example.week04.service;

import com.example.week04.dto.CommentRequestDto;
import com.example.week04.dto.ResponseDto;
import com.example.week04.entity.Comment;
import com.example.week04.entity.Post;
import com.example.week04.repository.CommentRepository;
import com.example.week04.repository.PostRepository;
import com.example.week04.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public ResponseDto<?> create(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Optional<Post> findPost = postRepository.findById(requestDto.getPostId());
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        Post post = findPost.get();
        Comment comment = new Comment(requestDto.getContent(), userDetails.getUsername(), post);
        return ResponseDto.success(commentRepository.save(comment));
    }

    public ResponseDto<?> update(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Optional<Comment> findComment = commentRepository.findById(id);
        if (!findComment.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }
        Comment comment = findComment.get();
        Optional<Post> findPost = postRepository.findById(requestDto.getPostId());
        if (!findPost.isPresent()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        if (!comment.getAuthor().equals(userDetails.getUsername())) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        Post post = findPost.get();

        comment.update(post, requestDto.getContent());
        return ResponseDto.success(comment);
    }

    public ResponseDto<?> delete(Long id, UserDetailsImpl userDetails) {
        Optional<Comment> findComment = commentRepository.findById(id);
        if (!findComment.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }
        Comment comment = findComment.get();
        if (!comment.getAuthor().equals(userDetails.getUsername())) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        commentRepository.delete(comment);
        return ResponseDto.success("success");
    }
}
