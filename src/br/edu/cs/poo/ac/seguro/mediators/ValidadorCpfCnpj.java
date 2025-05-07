package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.mediators.StringUtils;

public class ValidadorCpfCnpj {
    public static boolean ehCnpjValido(String cnpj) {
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma1 = 0;
        for (int i = 0; i < 12; i++) {
            soma1 += (cnpj.charAt(i) - '0') * pesos1[i];
        }
        int digito1 = soma1 % 11;
        digito1 = (digito1 < 2) ? 0 : 11 - digito1;

        int soma2 = 0;
        for (int i = 0; i < 12; i++) {
            soma2 += (cnpj.charAt(i) - '0') * pesos2[i];
        }
        soma2 += digito1 * pesos2[12];
        int digito2 = soma2 % 11;
        digito2 = (digito2 < 2) ? 0 : 11 - digito2;

        return (cnpj.charAt(12) - '0' == digito1) && (cnpj.charAt(13) - '0' == digito2);
    }
    public static boolean ehCpfValido(String cpf) {
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += (cpf.charAt(i) - '0') * (10 - i);
        }
        int digito1 = soma1 % 11;
        digito1 = (digito1 < 2) ? 0 : 11 - digito1;

        int soma2 = 0;
        for (int i = 0; i < 9; i++) {
            soma2 += (cpf.charAt(i) - '0') * (11 - i);
        }
        soma2 += digito1 * 2;
        int digito2 = soma2 % 11;
        digito2 = (digito2 < 2) ? 0 : 11 - digito2;

        if (!(cpf.charAt(9) - '0' == digito1) && (cpf.charAt(10) - '0' == digito2)) {
            return false;
        }
        return true;
    }
}