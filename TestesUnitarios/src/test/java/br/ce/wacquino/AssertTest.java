package br.ce.wacquino;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {

    @Test
    public void test() {
        Assert.assertTrue("Erro de comparação",true);
        Assert.assertFalse(false);

        Assert.assertEquals(1, 1);
        Assert.assertEquals(0.51, 0.51,0.01);
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer I = 5;
        Assert.assertEquals(Integer.valueOf(i),I);
        Assert.assertEquals(i, I.intValue());

        Assert.assertEquals("bola", "bola");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("bo"));

        Usuario usuario1 = new Usuario("Usuario1");
        Usuario usuario2 = new Usuario("Usuario1");
        Usuario usuario3 = usuario2;
        Usuario usuario4 = null;

        Assert.assertEquals(usuario1, usuario2);
        Assert.assertSame(usuario3, usuario2);
        Assert.assertNotSame(usuario1, usuario2);
        Assert.assertNull(usuario4);
    }
}
