package orla.cadastroprojetos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjetoDto(Long id, String nome, LocalDate dataDeCriacao, List<FuncionarioDto> funcionariosDto) {

    public ProjetoDto(Long id, String nome, LocalDate dataDeCriacao) {
        this(id, nome, dataDeCriacao, null);
    }
}