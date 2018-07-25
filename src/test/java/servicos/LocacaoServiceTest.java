package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static utils.DataUtils.isMesmaData;
import static utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector erro = new ErrorCollector();


    //assistir aula 09
    @Test
    public void testeLocacao() {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        //acao
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificacao
        erro.checkThat(locacao.getValor(),is(equalTo(5.0)));
        erro.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        erro.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(false));
    }

}
