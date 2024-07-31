package orla.cadastroprojetos.aplicacao;

import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import orla.cadastroprojetos.ExtensoesDeTeste;
import orla.cadastroprojetos.entidade.Funcionario;
import orla.cadastroprojetos.entidade.Projeto;
import orla.cadastroprojetos.excecoes.FuncionarioJaAssociadoException;
import orla.cadastroprojetos.repositorio.FuncionarioRepositorio;
import orla.cadastroprojetos.repositorio.ProjetoRepositorio;
import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtensionMethod({ExtensoesDeTeste.class})
class AdicionaFuncionarioNoProjetoTest {

    private IAdicionaFuncionarioNoProjeto adicionaFuncionarioNoProjeto;
    private ProjetoRepositorio projetoRepositorio;
    private FuncionarioRepositorio funcionarioRepositorio;
    private long idDoFuncionario;
    private long idDoProjeto;

    @BeforeEach
    void init() {
        idDoFuncionario = 1L;
        idDoProjeto = 1L;
        funcionarioRepositorio = mock(FuncionarioRepositorio.class);
        projetoRepositorio = mock(ProjetoRepositorio.class);
        adicionaFuncionarioNoProjeto = new AdicionaFuncionarioNoProjeto(funcionarioRepositorio, projetoRepositorio);
    }

    @Test
    void deveAdicionarFuncionarioNoProjeto() {
        ArgumentCaptor<Funcionario> capturadorDeFuncionario = ArgumentCaptor.forClass(Funcionario.class);
        ArgumentCaptor<Projeto> capturadorDeProjeto = ArgumentCaptor.forClass(Projeto.class);
        Funcionario funcionario = new Funcionario("Nome do funcionario", new Cpf("12312312345"),
                new Email("email@gmail.com"), 1500.);
        when(funcionarioRepositorio.findById(idDoFuncionario)).thenReturn(Optional.of(funcionario));
        Projeto projeto = new Projeto("Nome do projeto", LocalDate.now());
        when(projetoRepositorio.findById(idDoProjeto)).thenReturn(Optional.of(projeto));

        adicionaFuncionarioNoProjeto.adicionar(idDoFuncionario, idDoProjeto);

        verify(funcionarioRepositorio, times(1)).save(capturadorDeFuncionario.capture());
        verify(projetoRepositorio, times(1)).save(capturadorDeProjeto.capture());
        Projeto projetoCapturado = capturadorDeProjeto.getValue();
        Funcionario funcionarioCapturado = capturadorDeFuncionario.getValue();
        assertEquals(projetoCapturado.getFuncionarios(), singletonList(funcionarioCapturado));
    }

    @Test
    void deveLancarExcecaoSeNaoEncontrarFuncionarioComOIdPassado() {
        when(funcionarioRepositorio.findById(idDoFuncionario)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adicionaFuncionarioNoProjeto.adicionar(idDoFuncionario, idDoProjeto))
                .comMensagemDeErro("Funcionário não encontrado com o ID passado.");
    }

    @Test
    void deveLancarExcecaoSeNaoEncontrarProjetoComOIdPassado() {
        Funcionario funcionario = new Funcionario("Nome do funcionario", new Cpf("12312312345"),
                new Email("email@gmail.com"), 1500.);
        when(funcionarioRepositorio.findById(idDoFuncionario)).thenReturn(Optional.of(funcionario));
        when(projetoRepositorio.findById(idDoProjeto)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adicionaFuncionarioNoProjeto.adicionar(idDoFuncionario, idDoProjeto))
                .comMensagemDeErro("Projeto não encontrado com o ID passado.");
    }

    @Test
    void deveLancarExcecaoAoTentarAdicionarUmFuncionarioDuplicadoNoProjeto() {
        Funcionario funcionario = new Funcionario("Nome do funcionario", new Cpf("12312312345"),
                new Email("email@gmail.com"), 1500.);
        when(funcionarioRepositorio.findById(idDoFuncionario)).thenReturn(Optional.of(funcionario));
        Projeto projeto = new Projeto("Nome do projeto", LocalDate.now());
        projeto.adicionarFuncionario(funcionario);
        when(projetoRepositorio.findById(idDoProjeto)).thenReturn(Optional.of(projeto));

        assertThrows(FuncionarioJaAssociadoException.class, () -> adicionaFuncionarioNoProjeto.adicionar(idDoFuncionario, idDoProjeto))
                .comMensagemDeErro("Funcionário já está associado ao projeto.");
    }
}