package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    public static boolean ehCpfValido(String cpf) {
        if (cpf == null) return false;

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int dig = Character.getNumericValue(cpf.charAt(i));
            soma1 += dig * (10 - i);
            soma2 += dig * (11 - i);
        }

        int dig1 = soma1 % 11 < 2 ? 0 : 11 - (soma1 % 11);
        soma2 += dig1 * 2;
        int dig2 = soma2 % 11 < 2 ? 0 : 11 - (soma2 % 11);

        return dig1 == Character.getNumericValue(cpf.charAt(9)) &&
                dig2 == Character.getNumericValue(cpf.charAt(10));
    }

    public static boolean ehCnpjValido(String cnpj) {
        if (cnpj == null) return false;

        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma1 = 0, soma2 = 0;

        for (int i = 0; i < 12; i++) {
            int dig = Character.getNumericValue(cnpj.charAt(i));
            soma1 += dig * peso1[i];
            soma2 += dig * peso2[i];
        }

        int dig1 = soma1 % 11 < 2 ? 0 : 11 - (soma1 % 11);
        soma2 += dig1 * peso2[12];
        int dig2 = soma2 % 11 < 2 ? 0 : 11 - (soma2 % 11);

        return dig1 == Character.getNumericValue(cnpj.charAt(12)) &&
                dig2 == Character.getNumericValue(cnpj.charAt(13));
    }
}
