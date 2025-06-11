package br.edu.cs.poo.ac.seguro.entidades;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"placa"})

public class Veiculo implements Registro {
    private String placa;
    private int ano;
//    private SeguradoEmpresa proprietarioEmpresa;
//    private SeguradoPessoa proprietarioPessoa;
    private Segurado Proprietario;
    private CategoriaVeiculo categoria;

    @Override
    public String getIdUnico(){
        return this.placa;
    }
}
