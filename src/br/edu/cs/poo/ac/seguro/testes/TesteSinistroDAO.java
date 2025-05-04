package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TesteSinistroDAO {

    public static void teste01() {
        Veiculo veiculo = new Veiculo("XYZ9876", 2022, null, null, null);

        Sinistro sinistro = new Sinistro(
                "S001",
                veiculo,
                LocalDateTime.of(2024, 12, 1, 14, 30),
                LocalDateTime.now(),
                "admin",
                new BigDecimal("5000.00"),
                TipoSinistro.COLISAO
        );

        SinistroDAO dao = new SinistroDAO();
        boolean sucesso = dao.incluir(sinistro);

        System.out.println("[Teste 01 - Inclusão] " + (sucesso ? "SUCESSO" : "FALHA"));
    }

    public static void teste02() {
        SinistroDAO dao = new SinistroDAO();
        Sinistro sinistroRecuperado = dao.buscar("S001");

        if (sinistroRecuperado != null) {
            System.out.println("[Teste 02 - Busca] Sinistro encontrado: " + sinistroRecuperado.getNumero());
        } else {
            System.out.println("[Teste 02 - Busca] FALHA - Sinistro não encontrado");
        }
    }

    public static void main(String[] args) {
        teste01();
        teste02();
    }
}
