package com.example.jolvre.post.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.post.dto.postRequest;
import com.example.jolvre.post.dto.postResponse;
import com.example.jolvre.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "커뮤니티 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    //게시글 작성
    @Operation(summary = "게시글 작성")
    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> uploadPost(@ModelAttribute postRequest request,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.upload(request, principalDetails.getUser());
        return ResponseEntity.ok().build();
    }

    //전체 게시글 조회
    @Operation(summary = "전체 게시글 조회")
    @GetMapping("/list")
    public ResponseEntity<Page<postResponse>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        Page<postResponse> postList = postService.getAllPost((PageRequest) pageable);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    //특정 유저가 작성한 모든 글 조회
    @Operation(summary = "유저의 게시글 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<postResponse>> getPostsByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        Page<postResponse> postList = postService.getPostsByUserId(userId, (PageRequest) pageable);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    //특정 게시글 조회
    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/into/{postId}")
    public postResponse getPostById(@PathVariable("postId") Long postId) {
        postService.updateViews(postId);
        return postService.getPostById(postId);
    }

    //특정 게시글 삭제
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") Long postId,
                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.deletePost(postId, principalDetails.getUser());
    }

    //특정 게시글 수정
    @Operation(summary = "특정 게시글 수정")
    @PatchMapping(path = "/{postId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePost(@PathVariable("postId") Long postId,
                                        @ModelAttribute postRequest request,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.updatePost(request, postId, principalDetails.getUser());
        return ResponseEntity.ok().build();
    }

    //키워드 검색
    @Operation(summary = "제목 키워드 (str)로 검색")
    @GetMapping
    public Page<postResponse> searchByKeyword(@RequestParam(value = "keyword") String keyword,
                                              @RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        return postService.searchByKeyword(keyword, pageable);
    }
}