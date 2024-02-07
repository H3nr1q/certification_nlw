package com.rocketseat.certification_nlw.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCertificatonDTO;

@Service
public class VerifyIfHasCertificationUseCase {
    
    public boolean execute(VerifyHasCertificatonDTO verifyHasCertificatonDTO){
        if(verifyHasCertificatonDTO.getEmail().equals("carlos.henrique@teste.com.br") && verifyHasCertificatonDTO.getTechnology().equals("JAVA")){
            return true;
        }
        return false;
    }
}
