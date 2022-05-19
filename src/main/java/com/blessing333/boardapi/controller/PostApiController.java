package com.blessing333.boardapi.controller;

import com.blessing333.boardapi.controller.dto.PostCreateCommand;
import com.blessing333.boardapi.controller.dto.PostInformation;

import com.blessing333.boardapi.controller.dto.PostUpdateCommand;
import com.blessing333.boardapi.service.post.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @GetMapping("/api/v1/posts")
    public ResponseEntity<Page<PostInformation>> getPostsWithPaging(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostInformation> postInformation = postService.loadPostsWithPaging(pageable);
        return ResponseEntity.ok(postInformation);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostInformation> getPost(@PathVariable Long id) {
        PostInformation postInformation = postService.loadPostById(id);
        return ResponseEntity.ok(postInformation);
    }

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostInformation> createPost(@RequestBody @Valid PostCreateCommand commands) {
        PostInformation postInformation = postService.registerPost(commands);
        return ResponseEntity.status(HttpStatus.CREATED).body(postInformation);
    }

    @PutMapping("/api/v1/posts")
    public ResponseEntity<PostInformation> updatePost(@RequestBody @Valid PostUpdateCommand command) {
        PostInformation postInformation = postService.updatePost(command);
        return ResponseEntity.ok(postInformation);
    }
}
