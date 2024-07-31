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
class ProjetoTest {

    private String nomeDoProjeto;
    private LocalDate dataDeCriacao;

    @BeforeEach
    void init() {
        nomeDoProjeto = "Nome do projeto";
        dataDeCriacao = LocalDate.now();
    }

    @Test
    void deveInstanciarUmProjeto() {
        Projeto projeto = new Projeto(nomeDoProjeto, dataDeCriacao);

        assertEquals(projeto.getNome(), nomeDoProjeto);
        assertEquals(projeto.getDataDeCriacao(), dataDeCriacao);
    }

    @Test
    void deveSerPossivelAdicionarUmFuncionarioAoProjeto() {
        Projeto projeto = new Projeto(nomeDoProjeto, dataDeCriacao);
        String nomeDoFuncionario = "Nome do funcionário";
        Cpf cpf = new Cpf("12312312311");
        Email email = new Email("email.funcionario@gmail.com");
        Double salario = 1500.;
        Funcionario funcionario = new Funcionario(nomeDoFuncionario, cpf, email, salario);

        projeto.adicionarFuncionario(funcionario);

        assertEquals(projeto.getFuncionarios(), singletonList(funcionario));
    }

    @Test
    void deveLancarExcecaoAoTentarAdicionarUmFuncionarioInvalido() {
        Projeto projeto = new Projeto(nomeDoProjeto, dataDeCriacao);

        assertThrows(Excecao.class, () -> projeto.adicionarFuncionario(null))
                .comMensagemDeErro("Não é possível adicionar um funcionário inválido.");
    }

    @Test
    void deveLancarExcecaoSeDataDeCriacaoVierNulo() {
        assertThrows(Excecao.class, () -> new Projeto(nomeDoProjeto, null))
                .comMensagemDeErro("É obrigatório informar uma data de criação ao criar um projeto.");
    }

    @ParameterizedTest
    @MethodSource("nomeDoProjetoVazio")
    void deveLancarExcecaoSeNomeDoProjetoForVazio(String nomeDoProjeto) {
        assertThrows(Excecao.class, () -> new Projeto(nomeDoProjeto, dataDeCriacao))
                .comMensagemDeErro("É obrigatório informar um nome ao criar um projeto.");
    }

    private Stream<String> nomeDoProjetoVazio() {
        return Stream.of("", "    ", null);
    }
}