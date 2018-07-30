package servicos;

import builder.FilmeBuilder;
import builder.UsuarioBuilder;
import dao.LocacaoDao;
import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import matchers.MatchersProprios;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import utils.DataUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class LocacaoServiceTest {

    private LocacaoService service;
    private LocacaoDao dao;
    private SPCService spc;

    @Rule
    public ErrorCollector erro = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        service = new LocacaoService();
        dao = Mockito.mock(LocacaoDao.class);
        service.setLocacaoDao(dao);
        spc = Mockito.mock(SPCService.class);
        service.setSpcService(spc);
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        erro.checkThat(locacao.getValor(), is(equalTo(5.0)));
        erro.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
        erro.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancaExcecaoAoAlugarFilmeSemEstoque() throws Exception {

        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.filmeSemEstoque().agora());

        //acao
        service.alugarFilme(usuario, filmes);

        //forma elegante= quando apenas a exceção importa para o dev
        // se precisar capturar a mensagem usar a forma (robusta/nova)
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
        //cenario
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        //acao
        try {
            service.alugarFilme(null, filmes);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }

//        System.out.println("Forma robusta");
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = UsuarioBuilder.umUsuario().agora();

        exception.expect(LocadoraException.class);

        //acao
        service.alugarFilme(usuario, null);
//        System.out.println("Forma nova");
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario u1 = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        //acao
        Locacao retorno = service.alugarFilme(u1, filmes);

        //verificacao
        assertThat(retorno.getDataRetorno(), caiNumaSegunda());
    }

    @Test
    public void naoDeveAlugarFilmeParaNegativadoSpc() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario 2").agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        Mockito.when(spc.possuiNegativacao(usuario)).thenReturn(true);

        exception.expect(LocadoraException.class);
        exception.expectMessage("Usuario negativado");

        //acao
        service.alugarFilme(usuario, filmes);
    }


}

