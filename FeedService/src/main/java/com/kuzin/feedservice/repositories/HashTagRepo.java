package com.kuzin.feedservice.repositories;

import com.kuzin.feedservice.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepo extends JpaRepository<HashTag, Long> {

    HashTag findByName(String name);
}
