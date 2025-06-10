package br.edu.cs.poo.ac.seguro.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sinistro implements Serializable, Registro {
    private static final long serialVersionUID = 1L;

    private String numero;
    private Veiculo veiculo;
    private LocalDateTime dataHoraSinistro;
    private LocalDateTime dataHoraRegistro;
    private String usuarioRegistro;
    private BigDecimal valorSinistro;
    private TipoSinistro tipo;
    private int sequencial;
    private String numeroApolice;

    @Override
    public String getIdUnico(){
        return this.numero;
    }

    public String getNumApolice(){
        return this.numeroApolice;
    }

    public void setNumApolice(String numeroApolice){
        this.numeroApolice = numeroApolice;
    }

    public int getSequencial(){
        return this.sequencial;
    }

    public void setSequencial(int sequencial){
        this.sequencial = sequencial;
    }
}