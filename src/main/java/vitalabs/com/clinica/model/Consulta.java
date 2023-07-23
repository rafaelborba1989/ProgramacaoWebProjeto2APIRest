package vitalabs.com.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import vitalabs.com.clinica.controller.PacienteController;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Consulta{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String queixas;
    String diagnosticos;
    String procedimentos;
    String prescricoes;
    String observacoes;

    LocalDateTime deletedAt;
    @CreationTimestamp
    Date dataHoraConsulta;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Data
    public static class DtoRequest{
        String queixas;
        String diagnosticos;
        String procedimentos;
        String prescricoes;
        String observacoes;
        String medico_id;
        String paciente_id;

        public static Consulta convertToEntity(Consulta.DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Consulta.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<Consulta.DtoResponse> {
        String data;
        String hora;
        public static Consulta.DtoResponse convertToDto(Object p, ModelMapper mapper){
            return mapper.map(p, Consulta.DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(PacienteController.class).slash(id).withSelfRel());
            add(linkTo(PacienteController.class).withRel("Consulta"));
            add(linkTo(PacienteController.class).slash(id).withRel("delete"));
        }
    }
}
