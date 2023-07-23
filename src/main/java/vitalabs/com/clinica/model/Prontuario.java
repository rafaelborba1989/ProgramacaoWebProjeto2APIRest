package vitalabs.com.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import vitalabs.com.clinica.controller.PacienteController;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @CreationTimestamp
    LocalDateTime createdAt;
    String alergias;
    Character genero;
    String dataNascimento;
    String endereco;
    String limitacoes;
    String naturalidade;

    @Data
    public static class DtoRequest{
        String alergias;
        Character genero;
        String dataNascimento;
        String endereco;
        String limitacoes;

        public static Prontuario convertToEntity(Prontuario.DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Prontuario.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<Prontuario.DtoResponse> {

        public static Prontuario.DtoResponse convertToDto(Prontuario p, ModelMapper mapper){
            return mapper.map(p, Prontuario.DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(PacienteController.class).slash(id).withSelfRel());
            add(linkTo(PacienteController.class).withRel("Prontuario"));
            add(linkTo(PacienteController.class).slash(id).withRel("delete"));
        }
    }
}