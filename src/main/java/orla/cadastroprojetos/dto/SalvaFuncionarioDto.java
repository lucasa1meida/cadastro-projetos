package orla.cadastroprojetos.dto;

import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;

public record SalvaFuncionarioDto(String nome, Cpf cpf, Email email, Double salario) {
}