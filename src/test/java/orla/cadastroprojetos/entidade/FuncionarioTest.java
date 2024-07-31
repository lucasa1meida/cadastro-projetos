package orla.cadastroprojetos.entidade;

import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import orla.cadastroprojetos.ExtensoesDeTeste;
import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;
import orla.cadastroprojetos.utils.Excecao;

import java.time.LocalDate;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtensionMethod({ExtensoesDeTeste.class})
class FuncionarioTest {

    private String nome;
    private Cpf cpf;
    private Email email;
    private Double salario;

    @BeforeEach
    void init() {
        nome = "Nome do funcionario";
        cpf = new Cpf("36376344143");
        email = new Email("funcionario@hotmail.com");
        salario = 1500.;
    }

    @Test
    void deveInstanciarUmFuncionario() {
        Funcionario funcionario = new Funcionario(nome, cpf, email, salario);

        assertEquals(funcionario.getNome(), nome);
        assertEquals(funcionario.getCpf(), cpf);
        assertEquals(funcionario.getEmail(), email);
        assertEquals(funcionario.getSalario(), salario);
    }

    @Test
    void deveSerPossivelAdicionarUmProjeto() {
        Funcionario funcionario = new Funcionario(nome, cpf, email, salario);
        String nomeDoProjeto = "Nome do projeto";
        LocalDate dataDeCriacao = LocalDate.now();
        Projeto projeto = new Projeto(nomeDoProjeto, dataDeCriacao);

        funcionario.adicionarProjeto(projeto);

        assertEquals(funcionario.getProjetos(), singletonList(projeto));
    }

    @Test
    void deveLancarExcecaoAoTentarAdicionarUmProjetoInvalido() {
        Funcionario funcionario = new Funcionario(nome, cpf, email, salario);

        assertThrows(Excecao.class, () -> funcionario.adicionarProjeto(null))
                .comMensagemDeErro("Não é possível adicionar um projeto inválido.");
    }

    @Test
    void deveLancarExcecaoSeNaoInformarUmCpfAoCriarUmFuncionario() {
        assertThrows(Excecao.class, () -> new Funcionario(nome, null, email, salario))
                .comMensagemDeErro("É obrigatório informar um cpf ao criar um funcionário.");
    }

    @Test
    void deveLancarExcecaoSeNaoInformarUmEmailAoCriarUmFuncionario() {
        assertThrows(Excecao.class, () -> new Funcionario(nome, cpf, null, salario))
                .comMensagemDeErro("É obrigatório informar um email ao criar um funcionário.");
    }

    @ParameterizedTest
    @MethodSource("salarioInvalido")
    void deveLancarExcecaoSeNaoInformarUmSalarioAoCriarUmFuncionario(Double salarioFuncionario) {
        assertThrows(Excecao.class, () -> new Funcionario(nome, cpf, email, salarioFuncionario))
                .comMensagemDeErro("É obrigatório informar um salário ao criar um funcionário.");
    }

    @ParameterizedTest
    @MethodSource("nomeDoFuncionarioVazio")
    void deveLancarExcecaoSeNaoInformarUmNomeAoCriarUmFuncionario(String nomeDoFuncionario) {
        assertThrows(Excecao.class, () -> new Funcionario(nomeDoFuncionario, cpf, email, salario))
                .comMensagemDeErro("É obrigatório informar um nome ao criar um funcionário.");
    }

    private Stream<String> nomeDoFuncionarioVazio() {
        return Stream.of("", "    ", null);
    }

    private Stream<Double> salarioInvalido() {
        return Stream.of(0., -1., null);
    }
}