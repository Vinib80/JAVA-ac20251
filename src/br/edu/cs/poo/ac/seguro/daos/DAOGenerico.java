package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Registro;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import java.io.Serializable;

public abstract class DAOGenerico<T extends Registro> {

    protected CadastroObjetos cadastro;

    public boolean incluir(T obj) {
        if (buscar(obj.getIdUnico()) != null) {
            return false;
        } else {
            cadastro.incluir(obj, obj.getIdUnico());
            return true;
        }
    }

    public boolean alterar(T obj) {
        if (buscar(obj.getIdUnico()) == null){
            return false;
        } else {
            cadastro.alterar(obj, obj.getIdUnico());
            return true;
        }
    }

    public T buscar(String idUnico) {
        return (T) cadastro.buscar(idUnico);
    }

    public boolean excluir(String idUnico){
        if (buscar(idUnico) == null){
            return false;
        } else {
            cadastro.excluir(idUnico);
            return true;
        }
    }

    public Registro[] buscarTodos() {
        Serializable[] resultadoGenereico = cadastro.buscarTodos();

        Registro[] resultadoEspecifico = new Registro[resultadoGenereico.length];

        for (int i = 0; i < resultadoGenereico.length; i++) {
            resultadoEspecifico[i] = (Registro) resultadoGenereico[i];
        }

        return resultadoEspecifico;
    }
}
