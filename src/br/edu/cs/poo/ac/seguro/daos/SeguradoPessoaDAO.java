package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaDAO extends SeguradoDAO {
    @Override
    protected Class getClasseEntidade() {
        return SeguradoPessoa.class;
    }

    @Override
    public SeguradoPessoa buscar(String numero){
        return (SeguradoPessoa) super.buscar(numero);
    }
}