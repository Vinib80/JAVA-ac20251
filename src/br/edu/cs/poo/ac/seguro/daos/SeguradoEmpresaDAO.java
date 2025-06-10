package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaDAO extends SeguradoDAO{
    @Override
    protected Class getClasseEntidade() {
        return SeguradoEmpresa.class;
    }

    @Override
    public SeguradoEmpresa buscar(String numero){
        return (SeguradoEmpresa) super.buscar(numero);
    }
}
