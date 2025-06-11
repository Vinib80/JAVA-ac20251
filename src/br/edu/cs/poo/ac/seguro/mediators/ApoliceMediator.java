package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import br.edu.cs.poo.ac.seguro.daos.*;
import br.edu.cs.poo.ac.seguro.entidades.*;

public class ApoliceMediator {
    private SeguradoPessoaDAO daoSegPes = new SeguradoPessoaDAO();
    private SeguradoEmpresaDAO daoSegEmp = new SeguradoEmpresaDAO();
    private VeiculoDAO daoVel = new VeiculoDAO();
    private ApoliceDAO daoApo = new ApoliceDAO();
    private SinistroDAO daoSin = new SinistroDAO();
    private static ApoliceMediator instancia = new ApoliceMediator();
    private String numeroApolice;

    public static ApoliceMediator getInstancia() {
        return instancia;
    }

    private ApoliceMediator() {
    }

    public RetornoInclusaoApolice incluirApolice(DadosVeiculo dados) {
        String erro = validarTodosDadosVeiculo(dados);
        if (erro != null) {
            return new RetornoInclusaoApolice(null, erro);
        }

        erro = validarCpfCnpjValorMaximo(dados);
        if (erro != null) {
            return new RetornoInclusaoApolice(null, erro);
        }

        Segurado seguradoProprietario;
        if (dados.getCpfOuCnpj().length() == 11) {
            seguradoProprietario = daoSegPes.buscar(dados.getCpfOuCnpj());
            if (seguradoProprietario == null) {
                return new RetornoInclusaoApolice(null, "CPF inexistente no cadastro de pessoas");
            }
        } else {
            seguradoProprietario = daoSegEmp.buscar(dados.getCpfOuCnpj());
            if (seguradoProprietario == null) {
                return new RetornoInclusaoApolice(null, "CNPJ inexistente no cadastro de empresas");
            }
        }

        String numeroApolice;
        if (dados.getCpfOuCnpj().length() == 11) {
            numeroApolice = LocalDate.now().getYear() + "000" + dados.getCpfOuCnpj() + dados.getPlaca();
        } else {
            numeroApolice = LocalDate.now().getYear() + dados.getCpfOuCnpj() + dados.getPlaca();
        }

        if (buscarApolice(numeroApolice) != null) {
            return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
        }

        Veiculo veiculoParaApolice = daoVel.buscar(dados.getPlaca());

        if (veiculoParaApolice == null) {
            CategoriaVeiculo categoria = CategoriaVeiculo.obterPorCodigo(dados.getCodigoCategoria());
            veiculoParaApolice = new Veiculo(dados.getPlaca(), dados.getAno(), seguradoProprietario, categoria);
            daoVel.incluir(veiculoParaApolice);
        } else {
            veiculoParaApolice.setProprietario(seguradoProprietario);
            daoVel.alterar(veiculoParaApolice);
        }

        BigDecimal vpa = dados.getValorMaximoSegurado()
                .multiply(new BigDecimal("0.03"))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal vpb = vpa;
        if (seguradoProprietario.isEmpresa() && ((SeguradoEmpresa) seguradoProprietario).isEhLocadoraDeVeiculos()) {
            vpb = vpa.multiply(new BigDecimal("1.2")).setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal vpc = vpb.subtract(seguradoProprietario.getBonus().divide(new BigDecimal("10"), 2, RoundingMode.HALF_UP));

        BigDecimal premio = vpc.compareTo(BigDecimal.ZERO) > 0 ? vpc : BigDecimal.ZERO;
        premio = premio.setScale(2, RoundingMode.HALF_UP);

        BigDecimal franquia = vpb.multiply(new BigDecimal("1.3")).setScale(2, RoundingMode.HALF_UP);

        Apolice novaApolice = new Apolice(
                numeroApolice,
                veiculoParaApolice,
                franquia,
                premio,
                dados.getValorMaximoSegurado(),
                LocalDate.now()
        );
        daoApo.incluir(novaApolice);

        boolean houveSinistroAnoAnterior = false;
        Registro[] sinistrosRegistro = daoSin.buscarTodos();
        int anoAnterior = novaApolice.getDataInicioVigencia().getYear() - 1;

        for (Registro registro : sinistrosRegistro) {
            Sinistro sinistro = (Sinistro) registro;

            if (sinistro.getVeiculo() != null &&
                    sinistro.getVeiculo().getProprietario() != null &&
                    sinistro.getVeiculo().getProprietario().getIdUnico().equals(seguradoProprietario.getIdUnico())) {
                if (sinistro.getVeiculo().getProprietario().getIdUnico().equals(seguradoProprietario.getIdUnico())) {
                    if (sinistro.getDataHoraSinistro().getYear() == anoAnterior) {
                        houveSinistroAnoAnterior = true;
                        break;
                    }
                }
            }

        }

        if (!houveSinistroAnoAnterior) {
            BigDecimal valorDoBonus = premio.multiply(new BigDecimal("0.30")).setScale(2, RoundingMode.HALF_UP);

            seguradoProprietario.creditarBonus((valorDoBonus));

            if (seguradoProprietario.isEmpresa()) {
                daoSegEmp.alterar((SeguradoEmpresa) seguradoProprietario);
            } else {
                daoSegPes.alterar((SeguradoPessoa) seguradoProprietario);
            }
        }



        return new RetornoInclusaoApolice(numeroApolice, null);
    }

    public Apolice buscarApolice(String numero) {
        if (StringUtils.ehNuloOuBranco(numero)) {
            return null;
        }
        return daoApo.buscar(numero);
    }

    public String excluirApolice(String numero) {
        if (StringUtils.ehNuloOuBranco(numero)) {
            return "Número deve ser informado";
        }
        if (buscarApolice(numero) == null) {
            return "Apólice inexistente";
        }

        Apolice apoliceExcluir = daoApo.buscar(numero);
        Veiculo veiculoDaApolice = apoliceExcluir.getVeiculo();

        Registro[] todosOsSinistros = daoSin.buscarTodos();
        for (Registro reg : todosOsSinistros) {
            Sinistro sinistro = (Sinistro) reg;

            boolean mesmoVeiculo = sinistro.getVeiculo().equals(veiculoDaApolice);

            boolean mesmoAno = sinistro.getDataHoraSinistro().getYear() == apoliceExcluir.getDataInicioVigencia().getYear();

            if (mesmoVeiculo && mesmoAno) {
                return "Existe sinistro cadastrado para o veiculo em questão e no mesmo ano da apólice";
            }
        }

        daoApo.excluir(numero);
        return null;
    }

    private String validarTodosDadosVeiculo(DadosVeiculo dados) {
        if (dados == null) {
            return "Dados do veículo devem ser informados";
        }
        if (StringUtils.ehNuloOuBranco((dados.getCpfOuCnpj()))) {
            return "CPF ou CNPJ deve ser informado";
        }
        if (dados.getCpfOuCnpj().length() == 11) {
            if (!ValidadorCpfCnpj.ehCpfValido(dados.getCpfOuCnpj())) {
                return "CPF inválido";
            }
        } else if (dados.getCpfOuCnpj().length() == 14){
            if (!ValidadorCpfCnpj.ehCnpjValido((dados.getCpfOuCnpj()))) {
                return "CNPJ inválido";
            }
        }

        if (StringUtils.ehNuloOuBranco((dados.getPlaca()))) {
            return "Placa do veículo deve ser informada";
        }

        if (dados.getAno() < 2020 || dados.getAno() > 2025) {
            return "Ano tem que estar entre 2020 e 2025, incluindo estes";
        }
        if (dados.getValorMaximoSegurado() == null) {
            return "Valor máximo segurado deve ser informado";
        }
        if (CategoriaVeiculo.obterPorCodigo(dados.getCodigoCategoria()) == null) {
            return "Categoria inválida";
        }

        return null;
    }

    private String validarCpfCnpjValorMaximo(DadosVeiculo dados) {
        BigDecimal valorTabela = obterValorMaximoPermitido(dados.getAno(), dados.getCodigoCategoria());

        BigDecimal limiteInferior = valorTabela.multiply(new BigDecimal("0.75"));
        BigDecimal limiteSuperior = valorTabela;

        BigDecimal valorCliente = dados.getValorMaximoSegurado();

        if (valorCliente.compareTo(limiteInferior) < 0 || valorCliente.compareTo(limiteSuperior) > 0) {
            return "Valor máximo segurado deve estar entre 75% e 100% do valor do carro encontrado na categoria";
        }

        return null;
    }

    private BigDecimal obterValorMaximoPermitido(int ano, int codigoCat) {
        CategoriaVeiculo categoria = CategoriaVeiculo.obterPorCodigo(codigoCat);

        if (categoria == null) {
            return null;
        }
        for (PrecoAno pa : categoria.getPrecosAnos()) {
            if (pa.getAno() == ano) {
                return new BigDecimal(String.valueOf(pa.getPreco()));
            }
        }
        return null;
    }

}
