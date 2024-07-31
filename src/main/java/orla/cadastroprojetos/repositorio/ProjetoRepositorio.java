package orla.cadastroprojetos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import orla.cadastroprojetos.entidade.Projeto;

public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {
}