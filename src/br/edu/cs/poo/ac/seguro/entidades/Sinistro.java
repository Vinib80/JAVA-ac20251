package br.edu.cs.poo.ac.seguro.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor

public class Sinistro implements Serializable {
    private String numero;
    private Veiculo veiculo;
    private LocalDateTime dataHoraSinistro;
    private LocalDateTime dataHoraRegistro;
    private String usuarioRegistro;
    private BigDecimal valorSinistro;
    private TipoSinistro tipo;

}
