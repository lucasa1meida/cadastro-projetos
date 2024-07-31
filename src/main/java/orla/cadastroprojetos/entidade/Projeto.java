package orla.cadastroprojetos.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orla.cadastroprojetos.utils.Excecao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PROJETO")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false, unique = true)
    private String nome;

    @Column(name = "DATA_DE_CRIACAO", nullable = false)
    private LocalDate dataDeCriacao;

    @ManyToMany(mappedBy = "projetos")
    private List<Funcionario> funcionarios;

    public Projeto(String nome, LocalDate dataDeCriacao) {
        validarCamposObrigatorios(nome, dataDeCriacao);
        this.nome = nome;
        this.dataDeCriacao = dataDeCriacao;
        this.funcionarios = new ArrayList<>();
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        Excecao.quandoObjetoForNulo(funcionario, "Não é possível adicionar um funcionário inválido.");
        this.funcionarios.add(funcionario);
    }

    private void validarCamposObrigatorios(String nome, LocalDate dataDeCriacao) {
        Excecao.quandoStringForVazia(nome, "É obrigatório informar um nome ao criar um projeto.");
        Excecao.quandoObjetoForNulo(dataDeCriacao, "É obrigatório informar uma data de criação ao criar um projeto.");
    }
}