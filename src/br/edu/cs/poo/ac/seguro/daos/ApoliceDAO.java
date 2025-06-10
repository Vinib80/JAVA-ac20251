package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class ApoliceDAO extends DAOGenerico<Apolice> {
    @Override
    protected Class<Apolice> getClasseEntidade() {
        return Apolice.class;
    }
}
