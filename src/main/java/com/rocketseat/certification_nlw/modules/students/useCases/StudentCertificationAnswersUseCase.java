package com.rocketseat.certification_nlw.modules.students.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.questions.entities.QuestionsEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionsRepository;
import com.rocketseat.certification_nlw.modules.students.dto.StudentCertificationAnswersDTO;
import com.rocketseat.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.rocketseat.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.rocketseat.certification_nlw.modules.students.entities.StudentEntity;
import com.rocketseat.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.rocketseat.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private CertificationStudentRepository certificationRepository;
    
    public CertificationStudentEntity execute(StudentCertificationAnswersDTO studentCertificationAnswersDTO){
        
        List<QuestionsEntity> questionsEntity = questionsRepository.findByTechnology(studentCertificationAnswersDTO.getTechnology());

        studentCertificationAnswersDTO.getQuestionsAnswers()
            .stream().forEach(questionsAnswers -> {
                var question = questionsEntity.stream()
                    .filter(questions -> questions.getId().equals(questionsAnswers.getQuestionID())).findFirst().get();

                var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect()).findFirst().get();

                if (findCorrectAlternative.getId().equals(questionsAnswers.getAlternativeID())){
                    questionsAnswers.setCorrect(true);
                }else{
                    questionsAnswers.setCorrect(false);
                }
        });

        var student = studentRepository.findByEmail(studentCertificationAnswersDTO.getEmail());
        UUID studentID;
        if(!student.isEmpty()){
            var studentCreated = StudentEntity.builder().email(studentCertificationAnswersDTO.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        }
        else{
            studentID = student.get().getId();
        }

        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        CertificationStudentEntity certificationStudentEntity; = CertificationStudentEntity.builder()
            .technology(studentCertificationAnswersDTO.getTechnology())
            .studentID(studentID)
            .answersCertificationsEntity(answersCertifications)
            .buil();

            var certificationStudentCreated = certificationRepository.save(certificationStudentEntity);

        return certificationStudentCreated;

    }
    
}
