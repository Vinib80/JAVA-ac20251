package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

import java.time.LocalDate;

public class SeguradoEmpresaMediator {
    SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    SeguradoEmpresaDAO seguradoEmpresaDAO = new SeguradoEmpresaDAO();
    private static SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator() ;

    private SeguradoEmpresaMediator(){

    }

    public static SeguradoEmpresaMediator getInstancia(){
        if (instancia  == null) {
            instancia = new SeguradoEmpresaMediator();
        }
        return instancia;
    }

    public String validarCnpj(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ deve ser informado";
        } else if (cnpj.length() != 14) {
            return "CNPJ deve ter 14 caracteres";
        } else if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ com dígito inválido";
        } else if (buscarSeguradoEmpresa(cnpj) != null) {
            return "CNPJ do segurado empresa já existente";
        }
        return null;
    }
    public String validarFaturamento(double faturamento) {
        if (faturamento <= 0.0) {
            return "Faturamento deve ser maior que zero";
        }
        return null;
    }
    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        seguradoEmpresaDAO.incluir(seg);
        return null;
    }
    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        if (seguradoEmpresaDAO.buscar(seg.getCnpj()) == null) {
            return "CPF do segurado pessoa não existente";
        }

        boolean ok = seguradoEmpresaDAO.alterar(seg);
        if (!ok) {
            return "Não foi possível alterar o segurado pessoa";
        }

        return null;
    }
    public String excluirSeguradoEmpresa(String cnpj) {
        return null;
    }
    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return null;
        }
        return seguradoEmpresaDAO.buscar(cnpj);
    }
    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) {
            return msg;
        }

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) {
            return msg;
        }

        msg = seguradoMediator.validarDataCriacao(seg.getDataAbertura());
        if (msg != null) {
            return "Data da abertura deve ser informada";
        }

        msg = validarFaturamento(seg.getFaturamento());
        if (msg != null) {
            return msg;
        }

        msg = validarCnpj(seg.getCnpj());
        if (msg != null) {
            return msg;
        }



        return null;

    }

}