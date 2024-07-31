package orla.cadastroprojetos.utils;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private String endereco;

    public Email(String endereco) {
        validar(endereco);
        this.endereco = endereco;
    }

    private static void validar(String endereco) {
        String mensagemDeErro = "Email inválido.";
        Excecao.quandoStringForVazia(endereco, mensagemDeErro);
        Matcher matcher = EMAIL_PATTERN.matcher(endereco);
        boolean ehEmail = matcher.matches();
        Excecao.quandoVerdadeiro(!ehEmail, mensagemDeErro);
    }
}