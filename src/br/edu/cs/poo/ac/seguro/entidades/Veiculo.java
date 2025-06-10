package br.edu.cs.poo.ac.seguro.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Veiculo implements Registro {
    private String placa;
    private int ano;
//    private SeguradoEmpresa proprietarioEmpresa;
//    private SeguradoPessoa proprietarioPessoa;
    private CategoriaVeiculo categoria;
    private Segurado Proprietario;

    @Override
    public String getIdUnico(){
        return this.placa;
    }
}
