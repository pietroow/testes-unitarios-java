package servicos;

import builder.FilmeBuilder;
import builder.UsuarioBuilder;
import dao.LocacaoDao;
import dao.LocacaoDaoFake;
import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    public LocacaoService service;
    public LocacaoDao dao;
    public SPCService spc;

    @Parameter
    public List<Filme> filmes;

    @Parameter(value = 1)
    public Double valorLocacao;

    @Parameter(value = 2)
    public String cenario;

    public CalculoValorLocacaoTest() {
    }

    @Before
    public void setup() {
        service = new LocacaoService();
        dao = Mockito.mock(LocacaoDao.class);
        service.setLocacaoDao(dao);
        spc = Mockito.mock(SPCService.class);
        service.setSpcService(spc);

    }

    private static Filme filme1 = FilmeBuilder.umFilme().agora();
    private static Filme filme2 = FilmeBuilder.umFilme().agora();
    private static Filme filme3 = FilmeBuilder.umFilme().agora();
    private static Filme filme4 = FilmeBuilder.umFilme().agora();
    private static Filme filme5 = FilmeBuilder.umFilme().agora();
    private static Filme filme6 = FilmeBuilder.umFilme().agora();
    private static Filme filme7 = FilmeBuilder.umFilme().agora();


    @Parameters(name = "Teste {index} = {2}")
    public static Collection<Object[]> getParametros() {

        return Arrays.asList(new Object[][]
                {
                        {Arrays.asList(filme1, filme2), 8.0, "2 filmes sem desconto"},
                        {Arrays.asList(filme1, filme2, filme3), 11.0, "3 filmes 25%"},
                        {Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 filmes 50%"},
                        {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 filmes 75%"},
                        {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 filmes 100%"},
                        {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 filmes Sem desconto"}
                }
        );

    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario u1 = UsuarioBuilder.umUsuario().agora();

        //acao
        Locacao resultado = service.alugarFilme(u1, filmes);

        Assert.assertThat(resultado.getValor(), is(valorLocacao));
    }
}
