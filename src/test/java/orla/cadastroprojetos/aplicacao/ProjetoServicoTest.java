package orla.cadastroprojetos.aplicacao;

import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import orla.cadastroprojetos.ExtensoesDeTeste;
import orla.cadastroprojetos.dto.ProjetoDto;
import orla.cadastroprojetos.dto.SalvaProjetoDto;
import orla.cadastroprojetos.entidade.Projeto;
import orla.cadastroprojetos.excecoes.InvalidRequestBodyException;
import orla.cadastroprojetos.repositorio.ProjetoRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtensionMethod({ExtensoesDeTeste.class})
class ProjetoServicoTest {

    private IProjetoServico projetoServico;
    private ProjetoRepositorio projetoRepositorio;
    private String nomeDoProjeto;
    private LocalDate dataDeCriacao;
    private long idDoProjeto;

    @BeforeEach
    void init() {
        idDoProjeto = 123L;
        nomeDoProjeto = "Nome do projeto";
        dataDeCriacao = LocalDate.now();
        projetoRepositorio = mock(ProjetoRepositorio.class);
        projetoServico = new ProjetoServico(projetoRepositorio);
    }

    @Test
    void deveSalvarUmProjeto() {
        ArgumentCaptor<Projeto> capturadorDeProjeto = ArgumentCaptor.forClass(Projeto.class);
        SalvaProjetoDto salvaProjetoDto = new SalvaProjetoDto(nomeDoProjeto, dataDeCriacao);
        Projeto projetoEsperado = criarProjetoEsperadoPor(salvaProjetoDto);

        projetoServico.salvar(salvaProjetoDto);

        verify(projetoRepositorio, times(1)).save(capturadorDeProjeto.capture());
        Projeto projetoCapturado = capturadorDeProjeto.getValue();
        assertThat(projetoCapturado).usingRecursiveComparison().isEqualTo(projetoEsperado);
    }

    @Test
    void deveBuscarUmProjetoPorId() {
        Projeto projeto = new Projeto(nomeDoProjeto, dataDeCriacao);
        when(projetoRepositorio.findById(idDoProjeto)).thenReturn(Optional.of(projeto));
        ProjetoDto projetoDtoEsperado = criarProjetoDtoEsperadoPor(projeto);

        ProjetoDto projetoDtoRetornado = projetoServico.buscarPor(idDoProjeto);

        assertThat(projetoDtoRetornado).usingRecursiveComparison().isEqualTo(projetoDtoEsperado);
    }

    @Test
    void deveLancarExcecaoSeNaoVierOsDadosParaSalvarProjeto() {
        assertThrows(InvalidRequestBodyException.class, () -> projetoServico.salvar(null))
                .comMensagemDeErro("Dados para salvar projeto são obrigatórios.");
    }

    @Test
    void deveLancarExcecaoSeNaoAcharUmProjetoComOIdPassado() {
        when(projetoRepositorio.findById(idDoProjeto)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projetoServico.buscarPor(idDoProjeto))
                .comMensagemDeErro("Projeto não encontrado com o ID passado.");
    }

    private Projeto criarProjetoEsperadoPor(SalvaProjetoDto salvaProjetoDto) {
        return new Projeto(salvaProjetoDto.nome(), salvaProjetoDto.dataDeCriacao());
    }

    private ProjetoDto criarProjetoDtoEsperadoPor(Projeto projeto) {
        return new ProjetoDto(projeto.getId(), projeto.getNome(), projeto.getDataDeCriacao(), List.of());
    }
}