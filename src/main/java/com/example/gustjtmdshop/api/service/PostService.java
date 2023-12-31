package com.example.gustjtmdshop.api.service;

import com.example.gustjtmdshop.api.domain.Post;
import com.example.gustjtmdshop.api.domain.PostEditor;
import com.example.gustjtmdshop.api.exception.PostNotFound;
import com.example.gustjtmdshop.api.repository.PostRepository;
import com.example.gustjtmdshop.api.request.PostCreate;
import com.example.gustjtmdshop.api.request.PostEdit;
import com.example.gustjtmdshop.api.request.PostSearch;
import com.example.gustjtmdshop.api.response.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public Page<Post> getList(int page, int size, String sortField, String sortOrder) {

        Sort sort;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by(Sort.Order.desc(sortField));
        } else {
            sort = Sort.by(Sort.Order.asc(sortField));
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return postRepository.findAll(pageRequest);
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
