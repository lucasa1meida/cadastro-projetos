package orla.cadastroprojetos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import orla.cadastroprojetos.entidade.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {
}
