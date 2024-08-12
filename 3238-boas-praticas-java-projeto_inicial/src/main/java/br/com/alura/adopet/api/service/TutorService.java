package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.ValidacaoTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private List<ValidacaoTutor> validacoes;

    public void cadastrar(Tutor tutor){
        validacoes.forEach(v -> v.validar(tutor));
        tutorRepository.save(tutor);
    }

    public void atualizar(Tutor tutor){
        Tutor tutorUpdate = tutorRepository.getReferenceById(tutor.getId());
        tutorRepository.save(tutorUpdate);
    }
}
