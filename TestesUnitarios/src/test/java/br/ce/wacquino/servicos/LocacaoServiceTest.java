package br.ce.wacquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

class LocacaoServiceTest {

    @Test
    public void test() {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario1");
        Filme filme = new Filme("filme 1", 2, 5.0);

        //acão
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificaçao
        Assert.assertEquals(5.0, locacao.getValor(),0.01);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }
}