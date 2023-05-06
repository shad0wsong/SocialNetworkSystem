package com.kuzin.feedservice.repositories;

import com.kuzin.feedservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpaPostRepository")
public interface JpaPostRepository extends JpaRepository<Post, Long> {

}
