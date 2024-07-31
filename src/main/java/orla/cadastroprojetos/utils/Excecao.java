package orla.cadastroprojetos.utils;

import java.util.Objects;

public class Excecao extends RuntimeException {

    public Excecao(String mensagemDeErro) {
        super(mensagemDeErro);
    }

    public static void quandoObjetoForNulo(Object objeto, String mensagemDeErro) {
        if (Objects.isNull(objeto))
            entaoDisparar(mensagemDeErro);
    }

    public static void quandoNumeroForNuloOuMenorQueUm(Number numero, String mensagemDeErro) {
        if (Objects.isNull(numero) || numero.intValue() < 1)
            entaoDisparar(mensagemDeErro);
    }

    public static void quandoStringForVazia(String valor, String mensagemDeErro) {
        if (Objects.isNull(valor) || valor.trim().isEmpty())
            entaoDisparar(mensagemDeErro);
    }

    public static void quandoCpfInvalido(String cpf, String mensagemDeErro) {
        int quantidadeDeNumerosDeUmCpf = 11;
        Excecao.quandoStringForVazia(cpf, mensagemDeErro);

        String numeroCpfSemMascara = cpf.replaceAll("\\D", "");
        Excecao.quandoStringForVazia(numeroCpfSemMascara, mensagemDeErro);
        if (numeroCpfSemMascara.length() != quantidadeDeNumerosDeUmCpf)
            entaoDisparar(mensagemDeErro);
    }

    public static void quandoVerdadeiro(boolean verdadeiro, String mensagemDeErro) {
        if (verdadeiro)
            entaoDisparar(mensagemDeErro);
    }

    private static void entaoDisparar(String mensagemDeErro) {
        throw new Excecao(mensagemDeErro);
    }
}