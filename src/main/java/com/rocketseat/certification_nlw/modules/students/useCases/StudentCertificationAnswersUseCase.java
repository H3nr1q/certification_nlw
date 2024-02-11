package com.rocketseat.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.questions.entities.QuestionsEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionsRepository;
import com.rocketseat.certification_nlw.modules.students.dto.StudentCertificationAnswersDTO;
import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCertificatonDTO;
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

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
    
    public CertificationStudentEntity execute(StudentCertificationAnswersDTO studentCertificationAnswersDTO) throws Exception{

        var hasCertificatons = this.verifyIfHasCertificationUseCase.execute(new VerifyHasCertificatonDTO(studentCertificationAnswersDTO.getEmail(), studentCertificationAnswersDTO.getTechnology()));
        
        if(hasCertificatons){
            throw new Exception("Você já tirou sua certificação");
        }

        List<QuestionsEntity> questionsEntity = questionsRepository.findByTechnology(studentCertificationAnswersDTO.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        studentCertificationAnswersDTO.getQuestionsAnswers()
            .stream().forEach(questionsAnswers -> {
                var question = questionsEntity.stream()
                    .filter(questions -> questions.getId().equals(questionsAnswers.getQuestionID())).findFirst().get();

                var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect()).findFirst().get();

                if (findCorrectAlternative.getId().equals(questionsAnswers.getAlternativeID())){
                    questionsAnswers.setCorrect(true);
                    correctAnswers.getAndIncrement();
                }else{
                    questionsAnswers.setCorrect(false);
                }

                var answersCertificationsEntity = AnswersCertificationsEntity.builder()
                .answerIID(questionsAnswers.getAlternativeID())
                .questionsID(questionsAnswers.getQuestionID())
                .isCorrect(questionsAnswers.isCorrect())
                .build();

                answersCertifications.add(answersCertificationsEntity);
        });

        var student = studentRepository.findByEmail(studentCertificationAnswersDTO.getEmail());
        UUID studentID;
        if(student.isEmpty()){
            var studentCreated = StudentEntity.builder().email(studentCertificationAnswersDTO.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        }
        else{
            studentID = student.get().getId();
        }


        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
            .technology(studentCertificationAnswersDTO.getTechnology())
            .studentID(studentID)
            .grade(correctAnswers.get())
            .build();

        var certificationStudentCreated = certificationRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answersCertification -> {
            answersCertification.setCertificationID(certificationStudentEntity.getId());
            answersCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationsEntity(answersCertifications);

        certificationRepository.save(certificationStudentEntity);

        return certificationStudentCreated;

    }
    
}
