package orla.cadastroprojetos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FuncionarioDto(Long id, String nome, Cpf cpf, Email email, Double salario, List<ProjetoDto> projetosDto) {

    public FuncionarioDto(Long id, String nome, Cpf cpf, Email email, Double salario) {
        this(id, nome, cpf, email, salario, null);
    }
}