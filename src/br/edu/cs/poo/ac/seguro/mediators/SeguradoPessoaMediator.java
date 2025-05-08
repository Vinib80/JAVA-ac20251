package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

import java.time.LocalDate;

public class SeguradoPessoaMediator {

    private static SeguradoPessoaMediator instancia = new SeguradoPessoaMediator();

    private SeguradoPessoaDAO dao = new SeguradoPessoaDAO();
    private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();

    private SeguradoPessoaMediator() {}

    public static SeguradoPessoaMediator getInstancia() {
        return instancia;
    }

    public String validarCpf(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
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

    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        if (seg == null) {
            return "Segurado inválido";
        }

        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        if (seg.getDataNascimento() == null) {
            return "Data do nascimento deve ser informada";
        }
        if (seg.getDataNascimento().isAfter(LocalDate.now())) {
            return "Data do nascimento deve ser menor ou igual à data atual";
        }

        msg = validarCpf(seg.getCpf());
        if (msg != null) return msg;

        msg = validarRenda(seg.getRenda());
        if (msg != null) return msg;

        return null;
    }

    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        if (dao.buscar(seg.getCpf()) != null) {
            return "CPF do segurado pessoa já existente";
        }

        boolean ok = dao.incluir(seg);
        if (!ok) {
            return "Não foi possível incluir o segurado pessoa";
        }

        return null;
    }

    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        if (dao.buscar(seg.getCpf()) == null) {
            return "CPF do segurado pessoa não existente";
        }

        boolean ok = dao.alterar(seg);
        if (!ok) {
            return "Não foi possível alterar o segurado pessoa";
        }

        return null;
    }

    //
    public String excluirSeguradoPessoa(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF deve ser informado";
        }

        if (dao.buscar(cpf) == null) {
            return "CPF do segurado pessoa não existente";
        }

        boolean ok = dao.excluir(cpf);
        if (!ok) {
            return "Não foi possível excluir o segurado pessoa";
        }

        return null;
    }

    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return null;
        }

        return dao.buscar(cpf);
    }
}
