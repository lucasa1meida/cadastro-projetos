package orla.cadastroprojetos.aplicacao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orla.cadastroprojetos.entidade.Funcionario;
import orla.cadastroprojetos.entidade.Projeto;
import orla.cadastroprojetos.excecoes.FuncionarioJaAssociadoException;
import orla.cadastroprojetos.repositorio.FuncionarioRepositorio;
import orla.cadastroprojetos.repositorio.ProjetoRepositorio;

@Service
@RequiredArgsConstructor
public class AdicionaFuncionarioNoProjeto implements IAdicionaFuncionarioNoProjeto {

    private final FuncionarioRepositorio funcionarioRepositorio;
    private final ProjetoRepositorio projetoRepositorio;

    @Override
    public void adicionar(Long idDoFuncionario, Long idDoProjeto) {
        Funcionario funcionario = funcionarioRepositorio.findById(idDoFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID passado."));
        Projeto projeto = projetoRepositorio.findById(idDoProjeto)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o ID passado."));
        if (projeto.getFuncionarios().contains(funcionario))
            throw new FuncionarioJaAssociadoException("Funcionário já está associado ao projeto.");

        funcionario.adicionarProjeto(projeto);
        funcionarioRepositorio.save(funcionario);

        projeto.adicionarFuncionario(funcionario);
        projetoRepositorio.save(projeto);
    }
}