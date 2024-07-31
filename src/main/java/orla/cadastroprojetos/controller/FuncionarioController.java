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
import orla.cadastroprojetos.aplicacao.IFuncionarioServico;
import orla.cadastroprojetos.dto.FuncionarioDto;
import orla.cadastroprojetos.dto.SalvaFuncionarioDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final IFuncionarioServico funcionarioServico;

    @Operation(description = "Salvar funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso que o funcionário"),
            @ApiResponse(responseCode = "500", description = "Retorna mensagem de erro quando não há dados para salvar um funcionário")
    })
    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody SalvaFuncionarioDto salvaFuncionarioDto) {
        try {
            funcionarioServico.salvar(salvaFuncionarioDto);
            return ResponseEntity.ok("Funcionário salvo com sucesso.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o funcionário: " + ex.getMessage());
        }
    }

    @Operation(description = "Busca funcionário por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o funcionário"),
            @ApiResponse(responseCode = "400", description = "Funcionário não encontrado com o ID passado.")
    })
    @GetMapping("/{idDoFuncionario}")
    public ResponseEntity<Object> buscar(@PathVariable Long idDoFuncionario) {
        try {
            FuncionarioDto funcionarioDto = funcionarioServico.buscarPor(idDoFuncionario);
            return ResponseEntity.ok(funcionarioDto);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar o funcionário: " + ex.getMessage());
        }
    }
}