package orla.cadastroprojetos.aplicacao;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import orla.cadastroprojetos.dto.FuncionarioDto;
import orla.cadastroprojetos.dto.ProjetoDto;
import orla.cadastroprojetos.dto.SalvaFuncionarioDto;
import orla.cadastroprojetos.entidade.Funcionario;
import orla.cadastroprojetos.excecoes.InvalidRequestBodyException;
import orla.cadastroprojetos.repositorio.FuncionarioRepositorio;

import java.util.List;

@Service
@AllArgsConstructor
public class FuncionarioServico implements IFuncionarioServico {

    private final FuncionarioRepositorio funcionarioRepositorio;

    @Override
    public void salvar(SalvaFuncionarioDto salvaFuncionarioDto) {
        if (salvaFuncionarioDto == null)
            throw new InvalidRequestBodyException("Dados para salvar funcionário são obrigatórios.");

        Funcionario funcionario = new Funcionario(salvaFuncionarioDto.nome(), salvaFuncionarioDto.cpf(),
                salvaFuncionarioDto.email(), salvaFuncionarioDto.salario());
        funcionarioRepositorio.save(funcionario);
    }

    @Override
    public FuncionarioDto buscarPor(Long idDoFuncionario) {
        Funcionario funcionario = funcionarioRepositorio.findById(idDoFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID passado."));
        return mapearDtoPor(funcionario);
    }

    private FuncionarioDto mapearDtoPor(Funcionario funcionario) {
        List<ProjetoDto> projetosDto = funcionario.getProjetos()
                .stream()
                .map(projeto -> new ProjetoDto(projeto.getId(), projeto.getNome(), projeto.getDataDeCriacao()))
                .toList();

        return new FuncionarioDto(funcionario.getId(), funcionario.getNome(), funcionario.getCpf(), funcionario.getEmail(),
                funcionario.getSalario(), projetosDto);
    }
}