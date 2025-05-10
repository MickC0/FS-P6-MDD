package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByTopic_IdIn(List<Long> topicIds);
}
