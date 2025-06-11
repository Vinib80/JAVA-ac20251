package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DadosVeiculo {
    private String cpfOuCnpj;
    private String placa;
    private int ano;
    private BigDecimal valorMaximoSegurado;
    private int codigoCategoria;
    private boolean indicadorLocadora;

    public DadosVeiculo(String cpfOuCnpj, String placa, int ano, BigDecimal valorMaximoSegurado,
                        int codigoCategoria) {
        this.cpfOuCnpj = cpfOuCnpj;
        this.placa = placa;
        this.ano = ano;
        this.valorMaximoSegurado = valorMaximoSegurado;
        this.codigoCategoria = codigoCategoria;
    }

    void setCodigoCategoria(int codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }
    void setAno(int ano) {
        this.ano = ano;
    }
}

