package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;

import java.math.BigDecimal;

public class TesteApoliceDAO {

    public static void teste01() {
        Veiculo veiculo = new Veiculo("ABC1234", 2020, null, null, null);
        Apolice apolice = new Apolice(
                veiculo,
                new BigDecimal("1500.00"),
                new BigDecimal("900.00"),
                new BigDecimal("30000.00"),
                "APOL123"
        );

        ApoliceDAO dao = new ApoliceDAO();
        boolean sucesso = dao.incluir(apolice);

        System.out.println("[Teste 01 - Inclusão] " + (sucesso ? "SUCESSO" : "FALHA"));
    }

    public static void teste02() {
        ApoliceDAO dao = new ApoliceDAO();
        Apolice apoliceRecuperada = dao.buscar("APOL123");

        if (apoliceRecuperada != null) {
            System.out.println("[Teste 02 - Busca] Número encontrado: " + apoliceRecuperada.getNumero());
        } else {
            System.out.println("[Teste 02 - Busca] FALHA - Apólice não encontrada");
        }
    }

    public static void main(String[] args) {
        teste01();
        teste02();
    }
}
