package orla.cadastroprojetos.aplicacao;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import orla.cadastroprojetos.dto.FuncionarioDto;
import orla.cadastroprojetos.dto.ProjetoDto;
import orla.cadastroprojetos.dto.SalvaProjetoDto;
import orla.cadastroprojetos.entidade.Projeto;
import orla.cadastroprojetos.excecoes.InvalidRequestBodyException;
import orla.cadastroprojetos.repositorio.ProjetoRepositorio;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjetoServico implements IProjetoServico {

    private final ProjetoRepositorio projetoRepositorio;

    @Override
    public void salvar(SalvaProjetoDto salvaProjetoDto) {
        if (salvaProjetoDto == null)
            throw new InvalidRequestBodyException("Dados para salvar projeto são obrigatórios.");

        Projeto projeto = new Projeto(salvaProjetoDto.nome(), salvaProjetoDto.dataDeCriacao());
        projetoRepositorio.save(projeto);
    }

    @Override
    public ProjetoDto buscarPor(Long idDoProjeto) {
        Projeto projeto = projetoRepositorio.findById(idDoProjeto)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o ID passado."));

        return mapearDtoPor(projeto);
    }

    private ProjetoDto mapearDtoPor(Projeto projeto) {
        List<FuncionarioDto> funcionarioDtos = projeto.getFuncionarios().stream()
                .map(funcionario -> new FuncionarioDto(
                        funcionario.getId(), funcionario.getNome(), funcionario.getCpf(),
                        funcionario.getEmail(), funcionario.getSalario()
                ))
                .toList();

        return new ProjetoDto(projeto.getId(), projeto.getNome(), projeto.getDataDeCriacao(), funcionarioDtos);
    }
}