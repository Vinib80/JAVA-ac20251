package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {
    SeguradoMediator seguradoMediator;
    SeguradoEmpresaDAO seguradoEmpresaDAO;
    private SeguradoEmpresaMediator med = SeguradoEmpresaMediator.getInstancia();
    private static SeguradoEmpresaMediator instancia;

    private SeguradoEmpresaMediator(){

    }

    public static SeguradoEmpresaMediator getInstancia(){
        if (instancia  == null) {
            instancia = new SeguradoEmpresaMediator();
        }
        return instancia;
    }

    public String validarCnpj(String cnpj) {

        return null;
    }
    public String validarFaturamento(double faturamento) {
        return null;
    }
    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        return null;
    }
    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        return null;
    }
    public String excluirSeguradoEmpresa(String cnpj) {
        return null;
    }
    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return null;
    }
    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        return null;
    }
}