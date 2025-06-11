package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.cs.poo.ac.seguro.entidades.*;
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
            }

            if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().trim().isEmpty()) {
                excecao.getMensagens().add("Usuário de registro não pode ser nulo ou em branco.");
            }

            if (dados.getValorSinistro() <= 0) {
                excecao.getMensagens().add("Valor do sinistro deve ser maior que zero.");
            }

            if (TipoSinistro.getTipoSinistro(dados.getCodigoTipoSinistro()) == null) {
                excecao.getMensagens().add("Código do tipo de sinistro inválido.");
            }

            Apolice apoliceVigente = null;
            Registro[] todasOsRegistros = daoApolice.buscarTodos();
            for (Registro registro : todasOsRegistros){
                Apolice apolice = (Apolice) registro;
                if(apolice.getVeiculo().getPlaca().equals(dados.getPlaca())) {
                    LocalDate inicioApolice = apolice.getDataInicioVigencia();
                    LocalDate fim = inicioApolice.plusYears(1);
                    LocalDate dataSinistro = dados.getDataHoraSinistro().toLocalDate();

                    if (!dataSinistro.isAfter(inicioApolice) &&
                            dataSinistro.isBefore(fim)) {
                        apoliceVigente = apolice;
                        break;
                    }
                }
            }
            if (apoliceVigente == null) {
                excecao.getMensagens().add("Nenhuma apólice vigente encontrada para este veículo.");
            } else if (BigDecimal.valueOf(dados.getValorSinistro()).compareTo(apoliceVigente.getValorPremio()) > 0) {
                excecao.getMensagens().add("Valor do sinistro excede o valor máximo segurado da apólice.");
            }

            if (!excecao.getMensagens().isEmpty()) {
                throw excecao;
            }

            Sinistro[] todosSinistros = (Sinistro[]) daoSinistro.buscarTodos();
            List<Sinistro> sinistrosMesmaApolice = new ArrayList<>();
            for (Sinistro s : todosSinistros) {
                if (s.getNumeroApolice().equals(apoliceVigente.getNumero())) {
                    sinistrosMesmaApolice.add(s);
                }
            }

            int sequencial = 1;
            if (!sinistrosMesmaApolice.isEmpty()) {
                Collections.sort(sinistrosMesmaApolice, new ComparadorSinistroSequencial());
                int maiorSeq = sinistrosMesmaApolice.get(sinistrosMesmaApolice.size() - 1).getSequencial();
                sequencial = maiorSeq + 1;
            }

            String numeroSinistro = "S" + apoliceVigente.getNumero() + String.format("%03d", sequencial);

            Sinistro sinistro = new Sinistro(
                    numeroSinistro,
                    veiculo,
                    dados.getDataHoraSinistro(),
                    dataHoraAtual,
                    dados.getUsuarioRegistro(),
                    BigDecimal.valueOf(dados.getValorSinistro()),
                    TipoSinistro.getTipoSinistro(dados.getCodigoTipoSinistro())
            );

            sinistro.setNumeroApolice(apoliceVigente.getNumero());
            sinistro.setSequencial(sequencial);

            daoSinistro.incluir(sinistro);

            return numeroSinistro;
        }

        throw excecao;
    }

}

