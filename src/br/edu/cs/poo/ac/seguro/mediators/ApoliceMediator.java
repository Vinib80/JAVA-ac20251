
package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
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
        } else if(buscarApolice(LocalDate.now().getYear() + dados.getCpfOuCnpj() + dados.getPlaca()) !=  null){
            return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
        }
        else if(dados.getCodigoCategoria() < 1 || dados.getCodigoCategoria() > 5){
            return new RetornoInclusaoApolice(null, "Categoria inválida");
        } else if (dados.getCpfOuCnpj().length() == 11) {
            if (daoApo.buscar(dados.getCpfOuCnpj()) == null) {
                return new RetornoInclusaoApolice(null, "CPF inexistente no cadastro de pessoas");
            }
        } else if (dados.getCpfOuCnpj().length() == 14) {
            if (daoApo.buscar(dados.getCpfOuCnpj()) == null) {
                return new RetornoInclusaoApolice(null, "CNPJ inexistente no cadastro de empresas");
            }
        }
        return null;
    }

    public Apolice buscarApolice(String numero) {
        if(StringUtils.ehNuloOuBranco(numero)){
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
        } if (StringUtils.ehNuloOuBranco(dados.getPlaca())) {
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

        return null;
    }
    private BigDecimal obterValorMaximoPermitido(int ano, int codigoCat) {


        return null;
    }
}

