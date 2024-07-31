package orla.cadastroprojetos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orla.cadastroprojetos.aplicacao.IAdicionaFuncionarioNoProjeto;
import orla.cadastroprojetos.aplicacao.IProjetoServico;
import orla.cadastroprojetos.dto.ProjetoDto;
import orla.cadastroprojetos.dto.SalvaProjetoDto;

@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final IProjetoServico projetoServico;
    private final IAdicionaFuncionarioNoProjeto adicionaFuncionarioNoProjeto;

    @Operation(description = "Salvar projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso que o projeto foi salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Retorna mensagem de erro quando não há dados para salvar um projeto")
    })
    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody SalvaProjetoDto salvaProjetoDto) {
        try {
            projetoServico.salvar(salvaProjetoDto);
            return ResponseEntity.ok("Projeto salvo com sucesso.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o projeto: " + ex.getMessage());
        }
    }

    @Operation(description = "Adicionar um funcionário a um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso que o funcionário foi adicionado com sucesso ao projeto"),
            @ApiResponse(responseCode = "500", description = "Retorna mensagem de erro quando funcionário não foi encontrado com o ID passado."),
            @ApiResponse(responseCode = "500", description = "Retorna mensagem de erro quando projeto não foi encontrado com o ID passado.")
    })
    @PostMapping("/{idDoProjeto}/funcionarios/{idDoFuncionario}")
    public ResponseEntity<String> adicionarFuncionario(@PathVariable Long idDoFuncionario, @PathVariable Long idDoProjeto) {
        try {
            adicionaFuncionarioNoProjeto.adicionar(idDoFuncionario, idDoProjeto);
            return ResponseEntity.ok("Funcionário adicionado com sucesso ao projeto.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar o funcionário no projeto: " + ex.getMessage());
        }
    }

    @Operation(description = "Busca projeto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o projeto"),
            @ApiResponse(responseCode = "400", description = "Projeto não encontrado com o ID passado.")
    })
    @GetMapping("/{idDoProjeto}")
    public ProjetoDto buscar(@PathVariable Long idDoProjeto) {
        return projetoServico.buscarPor(idDoProjeto);
    }
}