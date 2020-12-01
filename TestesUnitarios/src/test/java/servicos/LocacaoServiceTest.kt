package servicos

import br.ce.wcaquino.entidades.Filme
import br.ce.wcaquino.entidades.Usuario
import br.ce.wcaquino.servicos.LocacaoService
import br.ce.wcaquino.utils.DataUtils
import org.junit.Assert
import org.junit.Test
import java.util.*

class LocacaoServiceTest {

    @Test
    fun main() {
        //cenario
        val service = LocacaoService()
        val usuario = Usuario("Usuario1")
        val filme = Filme("filme 1", 2, 5.0)

        //acão
        val locacao = service.alugarFilme(usuario, filme)

        //verificaçao
        Assert.assertTrue(locacao.valor == 5.0)
        Assert.assertTrue(DataUtils.isMesmaData(locacao.dataLocacao, Date()))
        Assert.assertTrue(DataUtils.isMesmaData(locacao.dataRetorno, DataUtils.obterDataComDiferencaDias(1)))
    }
}