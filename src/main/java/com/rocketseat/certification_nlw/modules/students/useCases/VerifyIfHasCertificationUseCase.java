package com.rocketseat.certification_nlw.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCertificatonDTO;
import com.rocketseat.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyIfHasCertificationUseCase {
    
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public boolean execute(VerifyHasCertificatonDTO verifyHasCertificatonDTO){
        var result = this.certificationStudentRepository.findByStudentEmailAndTechnology(verifyHasCertificatonDTO.getEmail(), verifyHasCertificatonDTO.getTechnology());
        if(!result.isEmpty()){
            return true;
        }
        return false;
    }
}
