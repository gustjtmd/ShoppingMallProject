package com.example.gustjtmdshop.api.request;


import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    /**
     *  빌더의 장점
     *  - 가독성에 좋다(값 생성에 대한 유연함)
     *  - 필요한 값만 받을 수 있다.
     *  - 객체의 불변성
     */
    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
