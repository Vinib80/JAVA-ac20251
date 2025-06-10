package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class VeiculoDAO extends DAOGenerico<Veiculo> {
    @Override
    protected Class<Veiculo> getClasseEntidade() {
        return Veiculo.class;
    }
}
