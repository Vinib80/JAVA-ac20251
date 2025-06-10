package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class SinistroDAO extends DAOGenerico<Sinistro> {
    @Override
    protected Class<Sinistro> getClasseEntidade() {
        return Sinistro.class;
    }
}
