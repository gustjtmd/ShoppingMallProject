package com.example.gustjtmdshop.api.domain;

import com.example.gustjtmdshop.api.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition="TEXT")
    @Comment("게시글 제목")
    private String title;

    @Column(nullable = false, columnDefinition="TEXT")
    @Comment("게시글 내용")
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }
}
