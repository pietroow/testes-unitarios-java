package dao;

import entidades.Locacao;

import java.util.List;

public class LocacaoDaoFake implements LocacaoDao {

    @Override
    public void salvar(Locacao locacao) {

    }

    @Override
    public List<Locacao> obterLocacoesPendentes() {
        return null;
    }


}
