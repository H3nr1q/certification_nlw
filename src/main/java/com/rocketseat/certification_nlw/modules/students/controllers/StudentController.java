package com.rocketseat.certification_nlw.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCertificatonDTO;
import com.rocketseat.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    
    //preciso usar minha useCase
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificatonDTO verifyHasCertificatonDTO){
        System.out.println(verifyHasCertificatonDTO);
        var result = this.verifyIfHasCertificationUseCase.execute(verifyHasCertificatonDTO);
        if(result){
            return "Usuário já fez a prova!";
        }
        return "Usuário pode fazer a prova!";

    }
}
