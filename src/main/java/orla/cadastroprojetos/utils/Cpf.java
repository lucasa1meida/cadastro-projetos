package orla.cadastroprojetos.utils;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cpf {

    private String numero;

    public Cpf(String numero) {
        validar(numero);
        this.numero = numero.replaceAll("\\D", "");
    }

    private static void validar(String numero) {
        Excecao.quandoCpfInvalido(numero, "CPF Inválido.");
    }
}