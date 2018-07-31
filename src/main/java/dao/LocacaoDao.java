package dao;

import entidades.Locacao;

import java.util.List;

public interface LocacaoDao {

    void salvar(Locacao locacao);


    List<Locacao> obterLocacoesPendentes();

}
