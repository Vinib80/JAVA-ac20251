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
            }

            if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().trim().isEmpty()) {
                excecao.getMensagens().add("Usuário de registro não pode ser nulo ou em branco.");
            }

            if (dados.getValorSinistro() <= 0) {
                excecao.getMensagens().add("Valor do sinistro deve ser maior que zero.");
            }

            if (TipoSinistro.buscarPorCodigo(dados.getCodigoTipoSinistro()) == null) {
                excecao.getMensagens().add("Código do tipo de sinistro inválido.");
            }

            Veiculo veiculo = daoVeiculo.buscar(dados.getPlaca());
            if (veiculo == null) {
                excecao.getMensagens().add("Placa não corresponde a nenhum veículo cadastrado.");
            }

            Apolice[] todasApolices = daoApolice.buscarTodos();
            Apolice apoliceVigente = null;
            if (veiculo != null) {
                for (Apolice apolice : todasApolices) {
                    if (apolice.getVeiculo().getPlaca().equals(veiculo.getPlaca())) {
                        LocalDateTime inicio = apolice.getInicioVigencia();
                        LocalDateTime fim = inicio.plusYears(1);
                        if (!dados.getDataHoraSinistro().isBefore(inicio) &&
                                dados.getDataHoraSinistro().isBefore(fim)) {
                            apoliceVigente = apolice;
                            break;
                        }
                    }
                }
                if (apoliceVigente == null) {
                    excecao.getMensagens().add("Nenhuma apólice vigente encontrada para este veículo.");
                } else if (dados.getValorSinistro() > apoliceVigente.getValorPremioMaximo()) {
                    excecao.getMensagens().add("Valor do sinistro excede o valor máximo segurado da apólice.");
                }
            }

            if (!excecao.getMensagens().isEmpty()) {
                throw excecao;
            }

            // Busca sinistros existentes com a mesma apólice
            Sinistro[] todosSinistros = daoSinistro.buscarTodos();
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
                    dados.getDataHoraSinistro(),
                    dados.getUsuarioRegistro(),
                    dados.getValorSinistro(),
                    TipoSinistro.buscarPorCodigo(dados.getCodigoTipoSinistro()),
                    veiculo
            );
            sinistro.setNumeroApolice(apoliceVigente.getNumero());
            sinistro.setSequencial(sequencial);

            daoSinistro.incluir(sinistro);

            return numeroSinistro;
        }

        throw excecao;
    }

}

