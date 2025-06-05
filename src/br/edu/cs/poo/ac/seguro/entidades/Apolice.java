package br.edu.cs.poo.ac.seguro.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class Apolice implements Serializable, Registro {
    private String numero;
    private Veiculo veiculo;
    private BigDecimal valorFranquia;
    private BigDecimal valorPremio;
    private BigDecimal valorMaximoSegurado;
    private LocalDate dataInicioVigencia;

    @Override
    public String getIdUnico(){
        return this.numero;
    }
}