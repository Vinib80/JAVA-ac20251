package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.StringUtils;
import br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj;

import javax.print.DocFlavor;

public class SeguradoPessoaMediator {
    SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    SeguradoPessoaDAO seguradoPessoaDAO = new SeguradoPessoaDAO();
    private SeguradoPessoaMediator med = SeguradoPessoaMediator.getInstancia();
    private static SeguradoPessoaMediator instancia;



    private SeguradoPessoaMediator(){

    }

    public static SeguradoPessoaMediator getInstancia(){
        if (instancia == null){
            instancia = new SeguradoPessoaMediator();
        }
        return instancia;
    }

    public String validarCpf(String cpf) {
        if (!StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF deve ser informado";
        }
        if (cpf.length() != 11) {
            return "CPF deve ter 11 caracteres";
        }
        if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF com dígito inválido";
        }
        return null;
    }
    public String validarRenda(double renda) {
        if (renda < 0) {
            return "Renda deve ser maior ou igual à zero";
        }
        return null;
    }
    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = null;
        if (!StringUtils.ehNuloOuBranco(seg.getNome())) {
            msg = "Nome deve ser informado";
        } else if (seg.getEndereco() == null) {
            msg = "Endereço deve ser informado";
        } else if (seg.getDataNascimento() == null) {
            msg = "Data do nascimento deve ser informada";
        } else if (validarCpf(seg.getCpf()) != null) {
            msg = validarCpf(seg.getCpf());
        } else if (validarRenda(seg.getRenda()) != null) {
            msg = validarRenda(seg.getRenda());
        }
        return msg;
    }
    public String alterarSeguradoPessoa(SeguradoPessoa seg) {

        return null;
    }
    public String excluirSeguradoPessoa(String cpf) {

        return null;
    }
    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {

        return seguradoPessoaDAO.buscar(cpf);
    }
    public String validarSeguradoPessoa(SeguradoPessoa seg) {

        return null;
    }
}