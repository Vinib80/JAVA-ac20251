package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteSinistroDAO extends TesteDAO {
    private SinistroDAO dao = new SinistroDAO();

    @Override
    protected Class getClasse() {
        return Sinistro.class;
    }

    @Test
    public void teste01() {
        String numero = "S001";
        Sinistro sin = new Sinistro(numero,new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user1", BigDecimal.TEN, TipoSinistro.COLISAO);
        cadastro.incluir(sin, numero);
        Sinistro res = dao.buscar(numero);
        Assertions.assertNotNull(res);
    }

    @Test
    public void teste02() {
        String numero = "S002";
        Sinistro sin = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user2", BigDecimal.ONE, TipoSinistro.FURTO);
        cadastro.incluir(sin, numero);
        Sinistro res = dao.buscar("S999");
        Assertions.assertNull(res);
    }

    @Test
    public void teste03() {
        String numero = "S003";
        Sinistro sin = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user3", BigDecimal.ZERO, TipoSinistro.INCENDIO);
        cadastro.incluir(sin, numero);
        boolean res = dao.excluir(numero);
        Assertions.assertTrue(res);
    }

    @Test
    public void teste04() {
        String numero = "S004";
        Sinistro sin = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user4", BigDecimal.valueOf(500), TipoSinistro.ENCHENTE);
        cadastro.incluir(sin, numero);
        boolean res = dao.excluir("S999");
        Assertions.assertFalse(res);
    }

    @Test
    public void teste05() {
        String numero = "S005";
        Sinistro sin = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user5", BigDecimal.valueOf(1500), TipoSinistro.DEPREDACAO);
        boolean res = dao.incluir(sin);
        Assertions.assertTrue(res);
        Sinistro ver = dao.buscar(numero);
        Assertions.assertNotNull(ver);
    }

    @Test
    public void teste06() {
        String numero = "S006";
        Sinistro sin = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user6", BigDecimal.valueOf(300), TipoSinistro.COLISAO);
        cadastro.incluir(sin, numero);
        boolean res = dao.incluir(sin);
        Assertions.assertFalse(res);
    }

    @Test
    public void teste07() {
        String numero = "S007";
        Sinistro sin = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user7", BigDecimal.valueOf(10), TipoSinistro.FURTO);
        boolean res = dao.alterar(sin);
        Assertions.assertFalse(res);
        Sinistro ver = dao.buscar(numero);
        Assertions.assertNull(ver);
    }

    @Test
    public void teste08() {
        String numero = "S008";
        Sinistro sin1 = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user8", BigDecimal.valueOf(900), TipoSinistro.INCENDIO);
        cadastro.incluir(sin1, numero);
        Sinistro sin2 = new Sinistro(numero, new Veiculo(), LocalDateTime.now(), LocalDateTime.now(), "user8b", BigDecimal.valueOf(800), TipoSinistro.ENCHENTE);
        boolean res = dao.alterar(sin2);
        Assertions.assertTrue(res);
    }
}
