package orla.cadastroprojetos.aplicacao;

import orla.cadastroprojetos.dto.FuncionarioDto;
import orla.cadastroprojetos.dto.SalvaFuncionarioDto;

public interface IFuncionarioServico {
    void salvar(SalvaFuncionarioDto salvaFuncionarioDto);

    FuncionarioDto buscarPor(Long idDoFuncionario);
}