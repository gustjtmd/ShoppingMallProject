package com.example.gustjtmdshop.api.repository;

import com.example.gustjtmdshop.api.domain.Post;
import com.example.gustjtmdshop.api.request.PostSearch;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
