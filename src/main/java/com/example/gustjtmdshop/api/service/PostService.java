package com.example.gustjtmdshop.api.service;

import com.example.gustjtmdshop.api.domain.Post;
import com.example.gustjtmdshop.api.repository.PostRepository;
import com.example.gustjtmdshop.api.request.PostCreate;
import com.example.gustjtmdshop.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // Case1. 저장한 데이터 Entity -> response로 응답하기
    // return postRepository.save(post);

    // Case2. 저장한 데이터의 primary_id -> response 응답하기
    // Client에서는 수신한 id를 post 글 조회 API를 통해서 글 데이터를 수신받음
    // return post.getId();
    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                        .title(postCreate.getTitle())
                        .content(postCreate.getContent())
                        .build();
        postRepository.save(post);

    }

    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;
    }

}
