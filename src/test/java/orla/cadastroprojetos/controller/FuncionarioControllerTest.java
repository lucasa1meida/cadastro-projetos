package orla.cadastroprojetos.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import orla.cadastroprojetos.aplicacao.FuncionarioServico;
import orla.cadastroprojetos.dto.FuncionarioDto;
import orla.cadastroprojetos.dto.SalvaFuncionarioDto;
import orla.cadastroprojetos.entidade.Funcionario;
import orla.cadastroprojetos.repositorio.FuncionarioRepositorio;
import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class FuncionarioControllerTest {

    @Autowired
    FuncionarioController funcionarioController;

    @Autowired
    FuncionarioServico funcionarioServico;

    @Autowired
    FuncionarioRepositorio funcionarioRepositorio;

    private SalvaFuncionarioDto salvaFuncionarioDto;

    @BeforeEach
    void init() {
        salvaFuncionarioDto = new SalvaFuncionarioDto("Nome do funcionario", new Cpf("12312312312"),
                new Email("emailDofun@gmail.com"), 2000.);
    }

    @Test
    void deveSalvarUmFuncionario() {
        ResponseEntity<String> response = funcionarioController.salvar(salvaFuncionarioDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário salvo com sucesso.", response.getBody());
        List<Funcionario> funcionarios = funcionarioRepositorio.findAll();
        Funcionario ultimoFuncionarioSalvo = funcionarios.get(funcionarios.size() - 1);
        assertEquals(ultimoFuncionarioSalvo.getNome(), salvaFuncionarioDto.nome());
        assertEquals(ultimoFuncionarioSalvo.getCpf(), salvaFuncionarioDto.cpf());
        assertEquals(ultimoFuncionarioSalvo.getEmail(), salvaFuncionarioDto.email());
        assertEquals(ultimoFuncionarioSalvo.getSalario(), salvaFuncionarioDto.salario());
    }

    @Test
    void naoDeveSalvarUmFuncionarioDuplicado() {
        funcionarioController.salvar(salvaFuncionarioDto);

        ResponseEntity<String> response = funcionarioController.salvar(salvaFuncionarioDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deveBuscarUmProjetoPorId() {
        Funcionario funcionario = new Funcionario("Nome do funcionario", new Cpf("12312312312"),
                new Email("emailDofun@gmail.com"), 2000.);
        funcionarioRepositorio.save(funcionario);
        List<Funcionario> funcionarios = funcionarioRepositorio.findAll();
        Funcionario ultimoFuncionarioSalvo = funcionarios.get(funcionarios.size() - 1);
        FuncionarioDto funcionarioDtoEsperado = criarFuncionarioDtoEsperado(ultimoFuncionarioSalvo);

        ResponseEntity<Object> response = funcionarioController.buscar(ultimoFuncionarioSalvo.getId());

        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(funcionarioDtoEsperado);
    }

    private static FuncionarioDto criarFuncionarioDtoEsperado(Funcionario ultimoFuncionarioSalvo) {
        return new FuncionarioDto(ultimoFuncionarioSalvo.getId(), ultimoFuncionarioSalvo.getNome(), ultimoFuncionarioSalvo.getCpf(),
                ultimoFuncionarioSalvo.getEmail(), ultimoFuncionarioSalvo.getSalario(), List.of());
    }
}