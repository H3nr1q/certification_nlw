package com.rocketseat.certification_nlw.modules.students.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCertificationAnswersDTO {
    
    private String email;
    
    private String technology;
    
    private List<QuestionsAnswerDTO> questionsAnswers;
}
