package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDateTime;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;

public class SinistroMediator {

    private VeiculoDAO daoVeiculo = new VeiculoDAO();
    private ApoliceDAO daoApolice = new ApoliceDAO();
    private SinistroDAO daoSinistro = new SinistroDAO();
    private static SinistroMediator instancia;

    public static SinistroMediator getInstancia() {
        if (instancia == null)
            instancia = new SinistroMediator();
        return instancia;
    }
    private SinistroMediator() {}

    public String incluirSinistro(DadosSinistro dados, LocalDateTime dataHoraAtual) throws ExcecaoValidacaoDados {
        ExcecaoValidacaoDados excecao = new ExcecaoValidacaoDados();

        if (dados == null) {
            excecao.getMensagens().add("Dados do sinistro não podem ser nulos.");
        } else {
            if (dados.getDataHoraSinistro() == null) {
                excecao.getMensagens().add("Data/hora do sinistro não pode ser nula.");
            } else if (!dados.getDataHoraSinistro().isBefore(dataHoraAtual)) {
                excecao.getMensagens().add("Data/hora do sinistro deve ser anterior à data/hora atual.");
            }

            if (dados.getPlaca() == null || dados.getPlaca().trim().isEmpty()) {
                excecao.getMensagens().add("Placa do veículo não pode ser nula ou em branco.");
            } else if (daoVeiculo.buscar(dados.getPlaca()) == null) {
                excecao.getMensagens().add("Placa não corresponde a nenhum veículo cadastrado.");
            }

            if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().trim().isEmpty()) {
                excecao.getMensagens().add("Usuário de registro não pode ser nulo ou em branco.");
            }

            if (dados.getValorSinistro() <= 0) {
                excecao.getMensagens().add("Valor do sinistro deve ser maior que zero.")

        return null;
    }
}