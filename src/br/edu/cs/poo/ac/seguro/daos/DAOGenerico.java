package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Registro;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import java.io.Serializable;

public abstract class DAOGenerico<T extends Registro> {

    private CadastroObjetos<T> cadastro;

    public DAOGenerico() {
        cadastro = new CadastroObjetos<>(getClasseEntidade());
    }

    protected abstract Class<T> getClasseEntidade();

    public void incluir(T obj) {
        cadastro.incluir(obj.getIdUnico(), obj);
    }

    public void alterar(T obj) {
        cadastro.alterar(obj.getIdUnico(), obj);
    }

    public T buscar(String idUnico) {
        return cadastro.buscar(idUnico);
    }

    public Registro[] buscarTodos() {
        return cadastro.buscarTodos();
    }
}
