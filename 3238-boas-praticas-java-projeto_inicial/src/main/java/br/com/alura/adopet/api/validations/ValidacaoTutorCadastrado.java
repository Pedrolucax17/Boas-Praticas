package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.exceptions.ValidationException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoTutorCadastrado implements ValidacaoTutor{

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public void validar(Tutor tutor) {
        boolean telefoneOuEmailJaCadastrado = tutorRepository.existsByTelefoneOrEmail(tutor.getTelefone(), tutor.getEmail());

        if (telefoneOuEmailJaCadastrado) {
            throw new ValidationException("Dados já cadastrados para outro tutor!");
        } else {
            tutorRepository.save(tutor);
        }
    }
}
