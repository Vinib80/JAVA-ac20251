package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaDAO extends SeguradoDAO {
    @Override
    protected Class<Segurado> getClasseEntidade() {
        return SeguradoPessoa.class;
    }

    public SeguradoPessoa buscar(String numero){
        return (SeguradoPessoa) super.buscar(numero);
    }
}