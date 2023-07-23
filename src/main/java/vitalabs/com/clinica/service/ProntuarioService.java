package vitalabs.com.clinica.service;

import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Prontuario;
import vitalabs.com.clinica.repository.ProntuarioRepository;

@Service
public class ProntuarioService{

    ProntuarioRepository repository;
    public ProntuarioService(ProntuarioRepository repository){
        this.repository = repository;
    }
}
