package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utils.DataUtils.isMesmaData;
import static utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector erro = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        service = new LocacaoService();
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 4, 5.0));

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        erro.checkThat(locacao.getValor(), is(equalTo(5.0)));
        erro.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        erro.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancaExcecaoAoAlugarFilmeSemEstoque() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

        //acao
        service.alugarFilme(usuario, filmes);

        //forma elegante= quando apenas a exceção importa para o dev
        // se precisar capturar a mensagem usar a forma (robusta/nova)
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 4, 5.0));

        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }

//        System.out.println("Forma robusta");
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");

        exception.expect(LocadoraException.class);

        //acao
        service.alugarFilme(usuario, null);
//        System.out.println("Forma nova");
    }

    @Test
    public void devePagar50PctNoFilme2() {

    }

    @Test
    public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario u1 = new Usuario("Pietro");
        List<Filme> filmes = Arrays.asList(
                new Filme("filme 1", 2, 4.0),
                new Filme("filme 2", 2, 4.0),
                new Filme("filme 3", 2, 4.0)
        );

        //acao
        Locacao resultado = service.alugarFilme(u1, filmes);

        Assert.assertThat(resultado.getValor(), is(11.0));
    }

    @Test
    public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario u1 = new Usuario("Pietro");
        List<Filme> filmes = Arrays.asList(
                new Filme("filme 1", 2, 4.0),
                new Filme("filme 2", 2, 4.0),
                new Filme("filme 3", 2, 4.0),
                new Filme("filme 4", 2, 4.0)
        );

        //acao
        Locacao resultado = service.alugarFilme(u1, filmes);

        Assert.assertThat(resultado.getValor(), is(13.0));
    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario u1 = new Usuario("Pietro");
        List<Filme> filmes = Arrays.asList(
                new Filme("filme 1", 2, 4.0),
                new Filme("filme 2", 2, 4.0),
                new Filme("filme 3", 2, 4.0),
                new Filme("filme 4", 2, 4.0),
                new Filme("filme 5", 2, 4.0)
        );

        //acao
        Locacao resultado = service.alugarFilme(u1, filmes);

        Assert.assertThat(resultado.getValor(), is(14.0));
    }

    @Test
    public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario u1 = new Usuario("Pietro");
        List<Filme> filmes = Arrays.asList(
                new Filme("filme 1", 2, 4.0),
                new Filme("filme 2", 2, 4.0),
                new Filme("filme 3", 2, 4.0),
                new Filme("filme 4", 2, 4.0),
                new Filme("filme 5", 2, 4.0),
                new Filme("filme 6", 2, 4.0)
        );

        //acao
        Locacao resultado = service.alugarFilme(u1, filmes);

        Assert.assertThat(resultado.getValor(), is(14.0));
    }

}

