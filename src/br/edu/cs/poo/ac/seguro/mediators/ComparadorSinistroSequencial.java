package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.entidades.Sinistro;

import java.util.Comparator;

public class ComparadorSinistroSequencial implements Comparator<Sinistro> {
    @Override
    public int compare(Sinistro s1, Sinistro s2) {
        String numeroSinistro1 = s1.getNumero();
        String numeroSinistro2 = s2.getNumero();

        String sequencial1 = numeroSinistro1.substring(numeroSinistro1.length()-3);
        String sequencial2 = numeroSinistro2.substring(numeroSinistro2.length()-3);

        int sequencialNum1 = Integer.parseInt(sequencial1);
        int sequencialNum2 = Integer.parseInt(sequencial2);

        return Integer.compare(sequencialNum1, sequencialNum2);
    }
}
