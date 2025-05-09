/*package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteApoliceDAO extends TesteDAO {

    private ApoliceDAO dao = new ApoliceDAO();

    @Override
    protected Class getClasse() {
        return Apolice.class;
    }

    @Test
    public void teste01() {
        String numero = "APOL001";
        Apolice apolice = gerarApolice(numero);
        cadastro.incluir(apolice, numero);
        Apolice res = dao.buscar(numero);
        Assertions.assertNotNull(res);
    }

    @Test
    public void teste02() {
        String numero = "APOL002";
        Apolice apolice = gerarApolice(numero);
        cadastro.incluir(apolice, numero);
        Apolice res = dao.buscar("APOL999");
        Assertions.assertNull(res);
    }

    @Test
    public void teste03() {
        String numero = "APOL003";
        Apolice apolice = gerarApolice(numero);
        cadastro.incluir(apolice, numero);
        boolean res = dao.excluir(numero);
        Assertions.assertTrue(res);
    }

    @Test
    public void teste04() {
        String numero = "APOL004";
        Apolice apolice = gerarApolice(numero);
        cadastro.incluir(apolice, numero);
        boolean res = dao.excluir("APOL999");
        Assertions.assertFalse(res);
    }

    @Test
    public void teste05() {
        String numero = "APOL005";
        Apolice apolice = gerarApolice(numero);
        boolean res = dao.incluir(apolice);
        Assertions.assertTrue(res);
        Apolice ver = dao.buscar(numero);
        Assertions.assertNotNull(ver);
    }

    @Test
    public void teste06() {
        String numero = "APOL006";
        Apolice apolice = gerarApolice(numero);
        cadastro.incluir(apolice, numero);
        boolean res = dao.incluir(apolice);
        Assertions.assertFalse(res);
    }

    @Test
    public void teste07() {
        String numero = "APOL007";
        Apolice apolice = gerarApolice(numero);
        boolean res = dao.alterar(apolice);
        Assertions.assertFalse(res);
        Apolice ver = dao.buscar(numero);
        Assertions.assertNull(ver);
    }

    @Test
    public void teste08() {
        String numero = "APOL008";
        Apolice apolice1 = gerarApolice(numero);
        cadastro.incluir(apolice1, numero);

        Apolice apolice2 = gerarApolice(numero);
        boolean res = dao.alterar(apolice2);
        Assertions.assertTrue(res);
    }

    private Apolice gerarApolice(String numero) {
        Veiculo veiculo = new Veiculo("XYZ1234", 2020, null, null, CategoriaVeiculo.BASICO);
        return new Apolice(veiculo,
                new BigDecimal("500.00"),
                new BigDecimal("300.00"),
                new BigDecimal("20000.00"),
                numero);
    }
}*/