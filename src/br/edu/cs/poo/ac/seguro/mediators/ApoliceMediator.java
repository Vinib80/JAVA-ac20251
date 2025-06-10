package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.PrecoAno;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

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
        if (validarTodosDadosVeiculo(dados) != null) {
            return new RetornoInclusaoApolice(null, validarTodosDadosVeiculo(dados));
        } else if (buscarApolice(LocalDate.now().getYear() + "000" + dados.getCpfOuCnpj() + dados.getPlaca()) != null) {
            return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
        } else if (buscarApolice(LocalDate.now().getYear() + dados.getCpfOuCnpj() + dados.getPlaca()) != null) {
            return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
        } else if (dados.getCodigoCategoria() < 1 || dados.getCodigoCategoria() > 5) {
            return new RetornoInclusaoApolice(null, "Categoria inválida");
        } else if (dados.getCpfOuCnpj().length() == 11) {
            if (daoSegPes.buscar(dados.getCpfOuCnpj()) == null) {
                return new RetornoInclusaoApolice(null, "CPF inexistente no cadastro de pessoas");
            }
            numeroApolice = LocalDate.now().getYear() + "000" + dados.getCpfOuCnpj() + dados.getPlaca();
        } else if (dados.getCpfOuCnpj().length() == 14) {
            if (daoSegEmp.buscar(dados.getCpfOuCnpj()) == null) {
                return new RetornoInclusaoApolice(null, "CNPJ inexistente no cadastro de empresas");
            }
            numeroApolice = LocalDate.now().getYear() + dados.getCpfOuCnpj() + dados.getPlaca();
        }

        Object segurado;
        if (dados.getCpfOuCnpj().length() == 11) {
            segurado = daoSegPes.buscar(dados.getCpfOuCnpj());
        } else {
            segurado = daoSegEmp.buscar(dados.getCpfOuCnpj());
        }

        BigDecimal vpa = dados.getValorMaximoSegurado()
                .multiply(new BigDecimal("0.03"))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal vpb = vpa;
        boolean ehEmpresaLocadora = dados.getCpfOuCnpj().length() == 14 && dados.isIndicadorLocadora();
        if (ehEmpresaLocadora) {
            vpb = vpa.multiply(new BigDecimal("1.2")).setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal bonus = BigDecimal.ZERO;
        if (segurado instanceof SeguradoPessoa) {
            bonus = ((SeguradoPessoa) segurado).getBonus();
        } else if (segurado instanceof SeguradoEmpresa) {
            bonus = ((SeguradoEmpresa) segurado).getBonus();
        }
        BigDecimal vpc = vpb.subtract(bonus.divide(new BigDecimal("10"), 2, RoundingMode.HALF_UP));

        BigDecimal premio = vpc.compareTo(BigDecimal.ZERO) > 0 ? vpc : BigDecimal.ZERO;

        BigDecimal franquia = vpb.multiply(new BigDecimal("1.3")).setScale(2, RoundingMode.HALF_UP);

        daoApo.incluir(new Apolice(numeroApolice, daoVel.buscar(dados.getPlaca()), franquia, premio, dados.getValorMaximoSegurado(), LocalDate.now()));

        return new RetornoInclusaoApolice(numeroApolice, null);
    }

    public Apolice buscarApolice(String numero) {
        if (StringUtils.ehNuloOuBranco(numero)) {
            return null;
        }
        return daoApo.buscar(numero);
    }

    public String excluirApolice(String numero) {
        return null;
    }

    private String validarTodosDadosVeiculo(DadosVeiculo dados) {
        if (dados == null) {
            return new RetornoInclusaoApolice(null, "Dados do veículo devem ser informados").getMensagemErro();
        } else if (StringUtils.ehNuloOuBranco(dados.getCpfOuCnpj())) {
            return new RetornoInclusaoApolice(null, "CPF ou CNPJ deve ser informado").getMensagemErro();
        } else if (dados.getCpfOuCnpj().length() == 11) {
            if (!ValidadorCpfCnpj.ehCpfValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null, "CPF inválido").getMensagemErro();
            }
        } else if (dados.getCpfOuCnpj().length() == 14) {
            if (!ValidadorCpfCnpj.ehCnpjValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null, "CNPJ inválido").getMensagemErro();
            }
        }
        if (StringUtils.ehNuloOuBranco(dados.getPlaca())) {
            return new RetornoInclusaoApolice(null, "Placa do veículo deve ser informada").getMensagemErro();
        } else if (dados.getAno() < 2020 || dados.getAno() > 2025) {
            return new RetornoInclusaoApolice(null, "Ano tem que estar entre 2020 e 2025, incluindo estes").getMensagemErro();
        } else if (dados.getValorMaximoSegurado() == null) {
            return new RetornoInclusaoApolice(null, "Valor máximo segurado deve ser informado").getMensagemErro();
        }
        obterValorMaximoPermitido(dados.getAno(), dados.getCodigoCategoria());
        return null;
    }

    private String validarCpfCnpjValorMaximo(DadosVeiculo dados) {
        BigDecimal valorMaxPermitido = obterValorMaximoPermitido(dados.getAno(), dados.getCodigoCategoria());
        if (valorMaxPermitido != null && dados.getValorMaximoSegurado().compareTo(valorMaxPermitido) > 0) {
            return "Valor máximo segurado acima do permitido";
        }
        return null;
    }

    private BigDecimal obterValorMaximoPermitido(int ano, int codigoCat) {
        if (ano < 2020 || ano > 2025) {
            return null;
        }

        if (codigoCat == 1) {
            return new BigDecimal("30000.00");
        } else if (codigoCat == 2) {
            return new BigDecimal("50000.00");
        } else if (codigoCat == 3) {
            return new BigDecimal("70000.00");
        } else if (codigoCat == 4) {
            return new BigDecimal("90000.00");
        } else if (codigoCat == 5) {
            return new BigDecimal("120000.00");
        } else {
            return null;
        }
    }

}
