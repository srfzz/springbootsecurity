package com.posts.demo.repository;

import com.posts.demo.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    Optional<PostEntity> findByUuid(UUID uuid);
}
