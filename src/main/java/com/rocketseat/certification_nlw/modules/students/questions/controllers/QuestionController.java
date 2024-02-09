package com.rocketseat.certification_nlw.modules.students.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

import com.rocketseat.certification_nlw.modules.students.questions.dto.AlternativesResultDTO;
import com.rocketseat.certification_nlw.modules.students.questions.dto.QuestionResultDTO;
import com.rocketseat.certification_nlw.modules.students.questions.entities.AlternativesEntity;
import com.rocketseat.certification_nlw.modules.students.questions.entities.QuestionsEntity;
import com.rocketseat.certification_nlw.modules.students.questions.repositories.QuestionsRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    
    @Autowired
    private QuestionsRepository questionsRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology){
        System.out.println("TECH ===" + technology);
        var result = this.questionsRepository.findByTechnology(technology);
        var toMap = result.stream().map(question -> mapQuestionToDTO(question)).collect(Collectors.toList());
        return toMap;
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionsEntity questions){
        var questionResultDTO = QuestionResultDTO.builder()
        .id(questions.getId())
        .technology(questions.getTechnology())
        .description(questions.getDescription())
        .build();

        List<AlternativesResultDTO> alternativesResultDTOs = questions.getAlternatives()
        .stream().map(alternative -> mapAlternativeDTO(alternative))
        .collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativesResultDTOs);
        return questionResultDTO;

    }

    static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternativesResultDTO){
        return AlternativesResultDTO.builder()
        .id(alternativesResultDTO.getId())
        .description(alternativesResultDTO.getDescription())
        .build();
    }
}
