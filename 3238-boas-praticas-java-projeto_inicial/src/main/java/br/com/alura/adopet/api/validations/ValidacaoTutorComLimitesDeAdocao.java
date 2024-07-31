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

public class ValidacaoTutorComLimitesDeAdocao implements ValidacaoSolicitacaoAdocao{

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto dto){
        boolean existeTutorComStatusAprovado = adocaoRepository.existByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO);
        int contador = 0;
        if (existeTutorComStatusAprovado) {
            contador = contador + 1;
        }
        if (contador == 5) {
            throw new ValidationException("Tutor chegou ao limite máximo de 5 adoções!");
        }
    }

}
