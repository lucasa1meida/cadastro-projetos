package orla.cadastroprojetos.entidade;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orla.cadastroprojetos.utils.Cpf;
import orla.cadastroprojetos.utils.Email;
import orla.cadastroprojetos.utils.Excecao;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FUNCIONARIO")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false, unique = true)
    private String nome;

    @AttributeOverride(name = "numero", column = @Column(name = "CPF", nullable = false, unique = true,
            columnDefinition = "VARCHAR(11) CHECK (CPF REGEXP '^[0-9]{11}$')"))
    private Cpf cpf;

    @AttributeOverride(name = "endereco", column = @Column(name = "EMAIL", nullable = false, unique = true))
    private Email email;

    @Column(name = "SALARIO", nullable = false)
    private Double salario;

    @ManyToMany
    @JoinTable(
            name = "FUNCIONARIO_PROJETO",
            joinColumns = @JoinColumn(name = "FUNCIONARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROJETO_ID"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"FUNCIONARIO_ID", "PROJETO_ID"})
    )
    private List<Projeto> projetos;

    public Funcionario(String nome, Cpf cpf, Email email, Double salario) {
        validarCamposObrigatorios(nome, cpf, email, salario);
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.salario = salario;
        this.projetos = new ArrayList<>();
    }

    public void adicionarProjeto(Projeto projeto) {
        Excecao.quandoObjetoForNulo(projeto, "Não é possível adicionar um projeto inválido.");
        this.projetos.add(projeto);
    }

    private static void validarCamposObrigatorios(String nome, Cpf cpf, Email email, Double salario) {
        Excecao.quandoStringForVazia(nome, "É obrigatório informar um nome ao criar um funcionário.");
        Excecao.quandoObjetoForNulo(cpf, "É obrigatório informar um cpf ao criar um funcionário.");
        Excecao.quandoObjetoForNulo(email, "É obrigatório informar um email ao criar um funcionário.");
        Excecao.quandoNumeroForNuloOuMenorQueUm(salario, "É obrigatório informar um salário ao criar um funcionário.");
    }
}