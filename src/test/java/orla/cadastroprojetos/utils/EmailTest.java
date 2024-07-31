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
class EmailTest {

    @Test
    void deveCriarUmEmail() {
        String endereco = "email@gmail.com";

        Email email = new Email("email@gmail.com");

        assertEquals(email.getEndereco(), endereco);
    }

    @ParameterizedTest
    @MethodSource("enderecoDoEmailInvalido")
    void deveLancarExcecaoSeEmailForInvalido(String enderecoInvalido) {
        assertThrows(Excecao.class, () -> new Email(enderecoInvalido)).comMensagemDeErro("Email inválido.");
    }

    private Stream<String> enderecoDoEmailInvalido() {
        return Stream.of("", "    ", null, "123", "email-invalido", "¨!@%&");
    }
}