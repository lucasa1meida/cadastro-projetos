package orla.cadastroprojetos.aplicacao;

import orla.cadastroprojetos.dto.ProjetoDto;
import orla.cadastroprojetos.dto.SalvaProjetoDto;

public interface IProjetoServico {
    void salvar(SalvaProjetoDto salvaProjetoDto);

    ProjetoDto buscarPor(Long idDoProjeto);
}