package vitalabs.com.clinica.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vitalabs.com.clinica.model.Usuario;
import vitalabs.com.clinica.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    UsuarioService service;
    ModelMapper mapper;

    public UsuarioController(UsuarioService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario.DtoResponse create(@RequestBody Usuario.DtoRequest u) {
        Usuario usuario = Usuario.DtoRequest.convertToEntity(u, mapper);
        this.service.createUsuario(usuario);

        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());
        System.out.println("Passou aqui");
        return response;

    }
    @GetMapping
    public List<Usuario.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual -> {
                    Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }

    @GetMapping("/{id}")
    public Usuario.DtoResponse getById(@PathVariable String id){
        Usuario usuario = this.service.getById(id);
        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());

        return response;
    }

    @PutMapping("/{id}")
    public Usuario.DtoResponse update(@RequestBody Usuario.DtoRequest dtoRequest, @PathVariable String id) {
        Usuario u = Usuario.DtoRequest.convertToEntity(dtoRequest, mapper);
        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(this.service.update(u, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.service.delete(id);
    }
}