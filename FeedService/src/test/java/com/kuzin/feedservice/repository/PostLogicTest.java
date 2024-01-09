package com.kuzin.feedservice.repository;


import com.kuzin.feedservice.entity.Post;
import com.kuzin.feedservice.repositories.JpaPostRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PostLogicTest {

    @Autowired
    private JpaPostRepository jpaPostRepository;

    @Test
    public void JpaPostRepositorySavePostTest() {
        Post post = Post.builder()
                .text("test")
                .base64Image("test")
                .views(0L)
                .build();

        Post savedPost = jpaPostRepository.save(post);

        Assertions.assertThat(jpaPostRepository).isNotNull();
        Assertions.assertThat(savedPost.getId()).isGreaterThan(0L);
    }
}
