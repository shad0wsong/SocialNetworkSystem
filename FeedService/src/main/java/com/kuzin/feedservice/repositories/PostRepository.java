package com.kuzin.feedservice.repositories;

import com.kuzin.feedservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
