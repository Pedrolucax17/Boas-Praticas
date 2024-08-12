package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exceptions.ValidationException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AbrigoService {
    @Autowired
    private AbrigoRepository abrigoRepository;

    public List<Abrigo> listar(){
        return abrigoRepository.findAll();
    }

    public void cadastrar(Abrigo abrigo){
        boolean abrigoJaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
        if (abrigoJaCadastrado) {
            throw new ValidationException("Dados já cadastrados para outro abrigo!");
        } else {
            abrigoRepository.save(abrigo);
        }
    }

    public List<Pet> listarPets(String idOuNome){
        List<Pet> pets = null;
        try {
            Long id = Long.parseLong(idOuNome);
            pets = abrigoRepository.getReferenceById(id).getPets();
        } catch (EntityNotFoundException enfe) {
            throw new ValidationException("Pet não encontrado!");
        } catch (NumberFormatException e) {
            try {
                pets = abrigoRepository.findByNome(idOuNome).getPets();
            } catch (EntityNotFoundException enfe) {
                throw new ValidationException("Pet não encontrado!");
            }
        }

        return pets;
    }

    public void cadastrarPet(String idOuNome, Pet pet){
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = abrigoRepository.getReferenceById(id);
            pet.setAbrigo(abrigo);
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            abrigoRepository.save(abrigo);
        } catch (EntityNotFoundException enfe) {
            throw new ValidationException("Dados já cadastrados para outro abrigo!");
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = abrigoRepository.findByNome(idOuNome);
                pet.setAbrigo(abrigo);
                pet.setAdotado(false);
                abrigo.getPets().add(pet);
                abrigoRepository.save(abrigo);
            } catch (EntityNotFoundException enfe) {
                throw new ValidationException("Dados já cadastrados para outro abrigo!");
            }
        }
    }
}
