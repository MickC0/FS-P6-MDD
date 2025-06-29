package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
    boolean existsByNormalizedName(String normalizedName);
}
