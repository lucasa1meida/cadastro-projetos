package orla.cadastroprojetos.utils;

import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import orla.cadastroprojetos.ExtensoesDeTeste;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtensionMethod({ExtensoesDeTeste.class})
class CpfTest {

    @Test
    void deveSerPossivelCriarUmCpf() {
        String numeroCpf = "12312312311";

        Cpf cpfCriado = new Cpf(numeroCpf);

        assertEquals(cpfCriado.getNumero(), numeroCpf);
    }

    @Test
    void deveCriarCpfSemMascaraAoEnviarCpfComMascara() {
        String numeroCpf = "123.123.123-11";
        String numeroCpfEsperado = numeroCpf.replaceAll("\\D", "");

        Cpf cpfCriado = new Cpf(numeroCpf);

        assertEquals(cpfCriado.getNumero(), numeroCpfEsperado);
    }

    @ParameterizedTest
    @MethodSource("cpfInvalido")
    void naoDeveSerPossivelCriarUmCpfInvalido(String numeroCpf) {
        assertThrows(Excecao.class, () -> new Cpf(numeroCpf)).comMensagemDeErro("CPF Inválido.");
    }

    private static Stream<String> cpfInvalido() {
        return Stream.of("123", "123.123.123-451", null, "", "    ");
    }
}