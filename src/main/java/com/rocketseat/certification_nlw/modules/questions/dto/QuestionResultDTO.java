package com.rocketseat.certification_nlw.modules.questions.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResultDTO {
    
    private UUID id;
    private String technology;
    private String description;

    private List<AlternativesResultDTO> alternatives;
}
