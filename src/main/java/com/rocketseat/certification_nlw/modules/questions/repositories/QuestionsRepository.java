package com.rocketseat.certification_nlw.modules.questions.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketseat.certification_nlw.modules.questions.entities.QuestionsEntity;

public interface QuestionsRepository extends JpaRepository<QuestionsEntity, UUID>{
 
    List<QuestionsEntity> findByTechnology(String technology);
}
