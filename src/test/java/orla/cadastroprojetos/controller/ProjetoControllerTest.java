package orla.cadastroprojetos.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import orla.cadastroprojetos.aplicacao.AdicionaFuncionarioNoProjeto;
import orla.cadastroprojetos.aplicacao.ProjetoServico;
import orla.cadastroprojetos.dto.SalvaProjetoDto;
import orla.cadastroprojetos.entidade.Funcionario;
import orla.cadastroprojetos.entidade.Projeto;
import orla.cadastroprojetos.repositorio.FuncionarioRepositorio;
import orla.cadastroprojetos.repositorio.ProjetoRepositorio;
import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProjetoControllerTest {

    @Autowired
    ProjetoController projetoController;

    @Autowired
    ProjetoServico projetoServico;

    @Autowired
    AdicionaFuncionarioNoProjeto adicionaFuncionarioNoProjeto;

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    private String nomeDoProjeto;
    private LocalDate dataDeCriacao;
    private SalvaProjetoDto salvaProjetoDto;
    private Funcionario funcionario;

    @BeforeEach
    void init() {
        funcionario = new Funcionario("Nome do funcionario 1", new Cpf("12312312311"),
                new Email("emailfunc1@gmail.com"), 1500.);
        nomeDoProjeto = "Nome para o projeto de teste";
        dataDeCriacao = LocalDate.now();
        salvaProjetoDto = new SalvaProjetoDto(nomeDoProjeto, dataDeCriacao);
    }

    @Test
    void deveSalvarUmProjeto() {
        ResponseEntity<String> response = projetoController.salvar(salvaProjetoDto);

        List<Projeto> projetos = projetoRepositorio.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Projeto salvo com sucesso.", response.getBody());
        Projeto ultimoProjetoSalvo = projetos.get(projetos.size() - 1);
        assertEquals(nomeDoProjeto, ultimoProjetoSalvo.getNome());
        assertEquals(dataDeCriacao, ultimoProjetoSalvo.getDataDeCriacao());
    }

    @Test
    void naoDeveSalvarUmProjetoDuplicado() {
        projetoController.salvar(salvaProjetoDto);

        ResponseEntity<String> response = projetoController.salvar(salvaProjetoDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deveAdicionarFuncionarioEmUmProjeto() {
        projetoController.salvar(salvaProjetoDto);
        List<Projeto> projetos = projetoRepositorio.findAll();
        Projeto ultimoProjetoSalvo = projetos.get(projetos.size() - 1);
        funcionarioRepositorio.save(funcionario);
        List<Funcionario> funcionarios = funcionarioRepositorio.findAll();
        Funcionario ultimoFuncionarioSalvo = funcionarios.get(funcionarios.size() - 1);

        projetoController.adicionarFuncionario(ultimoFuncionarioSalvo.getId(), ultimoProjetoSalvo.getId());

        Projeto projeto = projetoRepositorio.findById(ultimoProjetoSalvo.getId()).get();
        assertThat(projeto.getFuncionarios()).usingRecursiveComparison().isEqualTo(singletonList(funcionario));
    }

    @Test
    void naoDeveAdicionarFuncionarioDuplicadoEmUmProjeto() {
        projetoController.salvar(salvaProjetoDto);
        List<Projeto> projetos = projetoRepositorio.findAll();
        Projeto ultimoProjetoSalvo = projetos.get(projetos.size() - 1);
        funcionarioRepositorio.save(funcionario);
        List<Funcionario> funcionarios = funcionarioRepositorio.findAll();
        Funcionario ultimoFuncionarioSalvo = funcionarios.get(funcionarios.size() - 1);
        projetoController.adicionarFuncionario(ultimoFuncionarioSalvo.getId(), ultimoProjetoSalvo.getId());

        ResponseEntity<String> response = projetoController.adicionarFuncionario(ultimoFuncionarioSalvo.getId(), ultimoProjetoSalvo.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}