package br.ce.wacquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Before
    public void setUp() {
        service = new LocacaoService();
    }

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expection = ExpectedException.none();

    @Test
//    @Ignore // caso precise ignorar o teste
    public void deveAlugarFilme() throws Exception {

        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filme = asList(new Filme("filme 1", 2, 5.0));

        //acão
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificaçao
        Assert.assertEquals(5.0, locacao.getValor(),0.01);
        Assert.assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));

        //teste usando o ErrorCollector
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)),is(true));

        //Estudo assertThat()
        assertThat(locacao.getValor(), is(equalTo(5.0)));
        assertThat(locacao.getValor(), is(not(6.0)));
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

//    forma elegante
    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filme = asList(new Filme("filme 1", 0, 5.0));

        //acão
        service.alugarFilme(usuario, filme);
    }

//    forma robusta
    @Test
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque2() {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filme = asList(new Filme("filme 1", 0, 5.0));

        //acão
        try {
            service.alugarFilme(usuario, filme);
            fail("Deveria ter lançado uma excessão");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

//    forma nova
    @Test
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque3() throws Exception {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filme = asList(new Filme("filme 1", 0, 5.0));

        expection.expect(Exception.class);
        expection.expectMessage("Filme sem estoque");

        //acão
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {

        //cenario
        List<Filme> filmes = asList(new Filme("filme 1", 1, 5.0));

        //acão
        try {
            service.alugarFilme(null, filmes);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes = asList(new Filme("filme 1", 1, 4.0));

        expection.expect(LocadoraException.class);
        expection.expectMessage("Filme vazio");

        //acão
        service.alugarFilme(usuario, null);
    }

    @Test
    public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes =
                asList( new Filme("filme 1", 1, 4.0),
                        new Filme("filme 2", 1, 4.0),
                        new Filme("filme 3", 1, 4.0));

        //acão
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificaçao
        assertThat(resultado.getValor(), is(11.0));
    }

    @Test
    public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes =
                asList( new Filme("filme 1", 1, 4.0),
                        new Filme("filme 2", 1, 4.0),
                        new Filme("filme 3", 1, 4.0),
                        new Filme("filme 4", 1, 4.0));

        //acão
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificaçao
        assertThat(resultado.getValor(), is(13.0));
    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes =
                asList( new Filme("filme 1", 1, 4.0),
                        new Filme("filme 2", 1, 4.0),
                        new Filme("filme 3", 1, 4.0),
                        new Filme("filme 4", 1, 4.0),
                        new Filme("filme 5", 1, 4.0));

        //acão
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificaçao
        assertThat(resultado.getValor(), is(14.0));
    }

    @Test
    public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes =
                asList( new Filme("filme 1", 1, 4.0),
                        new Filme("filme 2", 1, 4.0),
                        new Filme("filme 3", 1, 4.0),
                        new Filme("filme 4", 1, 4.0),
                        new Filme("filme 5", 1, 4.0),
                        new Filme("filme 6", 1, 4.0));

        //acão
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificaçao
        assertThat(resultado.getValor(), is(14.0));
    }

    @Test
    public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {

        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes =
                asList( new Filme("filme 1", 1, 4.0));

        //acão
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificaçao
        boolean ehSegunda = DataUtils.verificarDiaSemana(resultado.getDataRetorno(), Calendar.MONDAY);
        assertTrue(ehSegunda);

    }
}