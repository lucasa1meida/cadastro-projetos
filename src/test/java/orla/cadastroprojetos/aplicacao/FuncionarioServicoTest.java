package orla.cadastroprojetos.aplicacao;

import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import orla.cadastroprojetos.ExtensoesDeTeste;
import orla.cadastroprojetos.dto.FuncionarioDto;
import orla.cadastroprojetos.dto.SalvaFuncionarioDto;
import orla.cadastroprojetos.entidade.Funcionario;
import orla.cadastroprojetos.excecoes.InvalidRequestBodyException;
import orla.cadastroprojetos.repositorio.FuncionarioRepositorio;
import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtensionMethod({ExtensoesDeTeste.class})
class FuncionarioServicoTest {

    private FuncionarioRepositorio funcionarioRepositorio;
    private IFuncionarioServico funcionarioServico;
    private Long idDoFuncionario;
    private String nomeDoFuncionario;
    private Cpf cpf;
    private Email email;
    private Double salario;

    @BeforeEach
    void init() {
        nomeDoFuncionario = "Nome do funcionario";
        cpf = new Cpf("12312312345");
        email = new Email("email@gmail.com");
        salario = 1500.;
        idDoFuncionario = 1123L;
        funcionarioRepositorio = mock(FuncionarioRepositorio.class);
        funcionarioServico = new FuncionarioServico(funcionarioRepositorio);
    }

    @Test
    void deveSalvarUmFuncionario() {
        ArgumentCaptor<Funcionario> capturadorDeFuncionario = ArgumentCaptor.forClass(Funcionario.class);
        SalvaFuncionarioDto salvaFuncionarioDto = new SalvaFuncionarioDto(nomeDoFuncionario, cpf, email, salario);
        Funcionario funcionarioEsperado = criarFuncionarioEsperadoPor(salvaFuncionarioDto);

        funcionarioServico.salvar(salvaFuncionarioDto);

        verify(funcionarioRepositorio, times(1)).save(capturadorDeFuncionario.capture());
        Funcionario funcionarioCapturado = capturadorDeFuncionario.getValue();
        assertThat(funcionarioCapturado).usingRecursiveComparison().isEqualTo(funcionarioEsperado);
    }

    @Test
    void deveBuscarUmFuncionarioPorId() {
        Funcionario funcionario = new Funcionario(nomeDoFuncionario, cpf, email, salario);
        FuncionarioDto funcionarioDtoEsperado = criarFuncionarioDtoEsperadoPor(funcionario);
        when(funcionarioRepositorio.findById(idDoFuncionario)).thenReturn(Optional.of(funcionario));

        FuncionarioDto funcionarioDtoRetornado = funcionarioServico.buscarPor(idDoFuncionario);

        assertThat(funcionarioDtoRetornado).usingRecursiveComparison().isEqualTo(funcionarioDtoEsperado);
    }

    @Test
    void deveLancarExcecaoSeNaoVierOsDadosParaSalvarFuncionario() {
        assertThrows(InvalidRequestBodyException.class, () -> funcionarioServico.salvar(null))
                .comMensagemDeErro("Dados para salvar funcionário são obrigatórios.");
    }

    @Test
    void deveLancarExcecaoSeNaoAcharUmFuncionarioComOIdPassado() {
        when(funcionarioRepositorio.findById(idDoFuncionario)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> funcionarioServico.buscarPor(idDoFuncionario))
                .comMensagemDeErro("Funcionário não encontrado com o ID passado.");
    }

    private FuncionarioDto criarFuncionarioDtoEsperadoPor(Funcionario funcionario) {
        return new FuncionarioDto(funcionario.getId(), funcionario.getNome(), funcionario.getCpf(), funcionario.getEmail(),
                funcionario.getSalario(), List.of());
    }


    private Funcionario criarFuncionarioEsperadoPor(SalvaFuncionarioDto salvaFuncionarioDto) {
        return new Funcionario(salvaFuncionarioDto.nome(), salvaFuncionarioDto.cpf(),
                salvaFuncionarioDto.email(), salvaFuncionarioDto.salario());
    }
}