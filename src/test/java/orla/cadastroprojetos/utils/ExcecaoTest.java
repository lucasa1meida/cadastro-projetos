package orla.cadastroprojetos.utils;

import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import orla.cadastroprojetos.ExtensoesDeTeste;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtensionMethod({ExtensoesDeTeste.class})
class ExcecaoTest {

    private String mensagemEsperada;

    @BeforeEach
    void init() {
        mensagemEsperada = "Mensagem da exceção";
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     "})
    void deveLancarUmaExcecaoQuandoStringForVazia(String texto) {
        assertThrows(Excecao.class, () -> Excecao.quandoStringForVazia(texto, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoStringForVazia(null, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertDoesNotThrow(() -> Excecao.quandoStringForVazia("Tem texto", mensagemEsperada));
    }

    @Test
    void deveLancarExcecaoQuandoNumeroForNuloOuMenorQueUm() {
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(null, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(0, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(-1, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(-99, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(-1L, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(0L, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(0., mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(-1., mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(new BigDecimal("0"), mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoNumeroForNuloOuMenorQueUm(new BigDecimal("-1"), mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertDoesNotThrow(() -> Excecao.quandoNumeroForNuloOuMenorQueUm(1, mensagemEsperada));
    }

    @Test
    void deveLancarExcecaoSeObjetoVierNulo() {
        assertThrows(Excecao.class, () -> Excecao.quandoObjetoForNulo(null, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertDoesNotThrow(() -> Excecao.quandoObjetoForNulo(new Object(), mensagemEsperada));
    }

    @Test
    void deveLancarExcecaoQuandoOValorPassadoForVerdadeiro() {
        assertThrows(Excecao.class, () -> Excecao.quandoVerdadeiro(true, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertDoesNotThrow(() -> Excecao.quandoVerdadeiro(false, mensagemEsperada));
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        String numeroComMenosDeOnzeDigitos = "1234567891";
        String numeroComMaisDeOnzeDigitos = "123456789123";
        String numeroApenasComCaracteresEspeciais = "*=!@#$%¨*+=";
        String numeroComCaracteresEspeciais = "123*&678978";
        String numeroCpfValidoComMascara = "164-926-937-81";
        String numeroCpfValidoSemMascara = "16492693781";
        assertThrows(Excecao.class, () -> Excecao.quandoCpfInvalido("", mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoCpfInvalido("   ", mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoCpfInvalido(null, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoCpfInvalido(numeroApenasComCaracteresEspeciais, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoCpfInvalido(numeroComCaracteresEspeciais, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoCpfInvalido(numeroComMenosDeOnzeDigitos, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertThrows(Excecao.class, () -> Excecao.quandoCpfInvalido(numeroComMaisDeOnzeDigitos, mensagemEsperada)).comMensagemDeErro(mensagemEsperada);
        assertDoesNotThrow(() -> Excecao.quandoCpfInvalido(numeroCpfValidoComMascara, mensagemEsperada));
        assertDoesNotThrow(() -> Excecao.quandoCpfInvalido(numeroCpfValidoSemMascara, mensagemEsperada));
    }
}