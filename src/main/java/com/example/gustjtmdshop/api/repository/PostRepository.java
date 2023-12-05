package com.example.gustjtmdshop.api.repository;

import com.example.gustjtmdshop.api.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
