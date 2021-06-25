package com.example.textapp.enums;

public enum SimboloEnum {

    SOMAR(0, "+"),
    SUBTRAIR(1, "-"),
    MULTIPLICAR(2, "X"),
    DIVIDIR(3, "/"),
    IGUAL(4, "="),
    DIFERENTE(5, "!="),
    PORCENTAGEM(6, "%"),
    LIMPAR(7, "CE"),
    REMOVER(8, "DEL"),
    DECIMAL(9, ","),
    ELEVAR(10, "^");

    private final int codigo;
    private final String simbolo;

    /**
     * Contrutor
     * */
    SimboloEnum(int codigo, String simbolo){
        this.codigo = codigo;
        this.simbolo = simbolo;
    }


    /**
     * Getters
     * */
    public int getCodigo() {
        return codigo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Metodos
     * */
    public static SimboloEnum toEnum(Integer codigo) {

        if (codigo == null || codigo < 0 || SimboloEnum.values().length <= codigo)
            return null;

        for (SimboloEnum e : SimboloEnum.values()) {

            if (codigo.equals(e.getCodigo()))
                return e;

        }

        throw new RuntimeException("Erro ao buscar pelo codigo: " + codigo);
    }

    public static SimboloEnum toEnum(String simbolo) {

        if (simbolo == null)
            return null;

        for (SimboloEnum e : SimboloEnum.values()) {

            if (simbolo.equals(e.getSimbolo()))
                return e;

        }

        throw new RuntimeException("Erro ao buscar pelo simbolo: " + simbolo);
    }

}
