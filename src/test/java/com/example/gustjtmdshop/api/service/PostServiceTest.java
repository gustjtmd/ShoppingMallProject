package com.example.gustjtmdshop.api.service;

import com.example.gustjtmdshop.api.domain.Post;
import com.example.gustjtmdshop.api.repository.PostRepository;
import com.example.gustjtmdshop.api.request.PostCreate;
import com.example.gustjtmdshop.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 작성")
    void test1(){
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        postService.write(postCreate);

        assertEquals(1L, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        PostResponse post = postService.get(requestPost.getId());


        assertEquals("foo", post.getTitle());
        assertEquals("bar", post.getContent());

    }
}