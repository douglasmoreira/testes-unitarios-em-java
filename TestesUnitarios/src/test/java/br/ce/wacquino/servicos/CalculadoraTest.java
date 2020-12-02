package br.ce.wacquino.servicos;

import br.ce.wcaquino.exception.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.servicos.Calculadora;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculadoraTest {

    public Calculadora calculadora;

    @Before
    public void setUp() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores(){

        //cenário
        int a = 5;
        int b = 3;

        //açao
        int resultado = calculadora.somar(a,b);

        //verificação
        assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores(){

        //cenário
        int a = 8;
        int b = 5;

        //açao
        int resultado = calculadora.subtrair(a,b);

        //verificação
        assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {

        //cenário
        int a = 6;
        int b = 3;

        //açao
        int resultado = calculadora.divide(a,b);

        //verificação
        assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoQuandoDividirPorZero() throws NaoPodeDividirPorZeroException {

        //cenário
        int a = 10;
        int b = 0;

        //açao
        int resultado = calculadora.divide(a,b);

        //verificação
        assertEquals(2, resultado);
    }
}
