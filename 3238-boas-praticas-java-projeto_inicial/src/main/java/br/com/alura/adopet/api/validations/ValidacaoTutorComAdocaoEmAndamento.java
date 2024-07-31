package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidationException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao{

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto dto){
        boolean existeTutorAguardandoAvaliacao = adocaoRepository.existByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (existeTutorAguardandoAvaliacao) {
            throw new ValidationException("Tutor já possui outra adoção aguardando avaliação!");
        }
    }

}
