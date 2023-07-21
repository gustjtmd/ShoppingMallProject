package com.example.gustjtmdshop.api.controller;

import com.example.gustjtmdshop.api.domain.Post;
import com.example.gustjtmdshop.api.request.PostCreate;
import com.example.gustjtmdshop.api.response.PostResponse;
import com.example.gustjtmdshop.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


/**
 * Controller와 RestController의 차이점
 * 근본적인 차이점은 @Controller의 역할은 Model 객체를 만들어 데이터를 담고 View를 찾는 것이지만,
 * @RestController는 단순히 객체만을 반환하고 객체 데이터는 JSON 또는 XML 형식으로 HTTP 응답에 담아서 전송합니다.
 * 물론 @Controller와 @ResponseBody를 사용하여 만들 수 있지만 이러한 방식은
 * RESTful 웹서비스의 기본 동작이기 때문에 Spring은 @Controller와 @ResponseBody의 동작을 조합한
 * @RestController을 도입했습니다.
 *
 * posts 요청시
 * title이나 content 입력 없을시
 * ExceptionController로 이동
 * 데이터 검증- 2 보기
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    // Case1. 저장한 데이터 Entity -> response로 응답하기
    // return postService.write(request);

    // Case2. 저장한 데이터의 primary_id -> response 응답하기
    // Long postId = postService.write(request);
    // return Map.of("postId", postId);

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {

        postService.write(request);
    }

    /**
     *  request 클래스
     *  response 클래스 분리했음
     */

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id){

        PostResponse response = postService.get(id);
        return response;
    }
}
