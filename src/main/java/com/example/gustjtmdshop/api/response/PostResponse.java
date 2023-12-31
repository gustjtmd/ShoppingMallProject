package com.example.gustjtmdshop.api.response;


import com.example.gustjtmdshop.api.BaseTimeEntity;
import com.example.gustjtmdshop.api.domain.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 클래스
 * (타이틀 값을 10글자로 잘라주세요)
 */

@Getter
public class PostResponse extends BaseTimeEntity {

    private final Long id;
    private final String title;
    private final String content;


    // 생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
