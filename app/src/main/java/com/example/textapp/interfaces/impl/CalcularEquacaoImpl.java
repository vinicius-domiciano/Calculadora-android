package com.example.textapp.interfaces.impl;

import com.example.textapp.enums.SimboloEnum;
import com.example.textapp.interfaces.CalcularAvancado;
import com.example.textapp.interfaces.CalcularEquacao;
import com.example.textapp.interfaces.CalcularOperacaoBasica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalcularEquacaoImpl implements CalcularEquacao {

    private final CalcularOperacaoBasica calculadoraBasica;
    private final CalcularAvancado calculadoraAvancado;

    public CalcularEquacaoImpl() {
        this.calculadoraBasica = new CalcularOperacaoBasicaImpl();
        this.calculadoraAvancado = new CalcularAvancadoImpl();
    }

    private List<String> numeros;
    private List<String> op;

    @Override
    public Object calcularPorPEMDAS(String value) {
        numeros = new ArrayList<>();
        op = new ArrayList<>();

        numeros.addAll(Arrays.asList(value.split("[+\\-/X^]")));
        op.addAll(Arrays.asList(value.replaceAll("([0-9])|\\.|%", "").split("")));
        op.remove("");
        numeros.remove("");

        if (String.valueOf(value.charAt(0)).equals(SimboloEnum.SUBTRAIR.getSimbolo())) {
            numeros.set(0, "-" + numeros.get(0));
            op.remove(0);
        }


        while (true) {
            int indiceMultiplicacao = op.indexOf(SimboloEnum.MULTIPLICAR.getSimbolo());
            int indiceDivisao = op.indexOf(SimboloEnum.DIVIDIR.getSimbolo());
            int indiceAdicao = op.indexOf(SimboloEnum.SOMAR.getSimbolo());
            int indiceSubitracao = op.indexOf(SimboloEnum.SUBTRAIR.getSimbolo());
            int indiceElevar = op.indexOf(SimboloEnum.ELEVAR.getSimbolo());

            if ( indiceElevar >= 0 ) {

                this.execultarEquacao(SimboloEnum.ELEVAR);

            }else if ( indiceMultiplicacao >= 0 && (indiceMultiplicacao < indiceDivisao || indiceDivisao < 0)) {

                this.execultarEquacao(SimboloEnum.MULTIPLICAR);

            } else if (indiceDivisao >= 0 && (indiceDivisao < indiceMultiplicacao || indiceMultiplicacao < 0) ) {

                this.execultarEquacao(SimboloEnum.DIVIDIR);

            } else if (indiceAdicao >= 0 && (indiceAdicao < indiceSubitracao || indiceSubitracao < 0)) {

                this.execultarEquacao(SimboloEnum.SOMAR);

            }else if (indiceSubitracao >= 0 && (indiceSubitracao < indiceAdicao || indiceAdicao < 0)) {

                this.execultarEquacao(SimboloEnum.SUBTRAIR);

            }else {
                break;
            }
        }

        return new BigDecimal(numeros.get(0));
    }

    @Override
    public String formatarEquacaoBrAPadrao(String value) {
        String valueStr = String.valueOf(value);

        valueStr = valueStr.replaceAll("\\.", "");
        return valueStr.replaceAll(",", ".");
    }

    private void execultarEquacao(SimboloEnum simbolo) {

        String vlStr1 = numeros.get(op.indexOf(simbolo.getSimbolo()));
        String vlStr2 = numeros.get(op.indexOf(simbolo.getSimbolo()) + 1);

        if (vlStr2.contains(SimboloEnum.PORCENTAGEM.getSimbolo())) {
            this.calcularPorcentagem(simbolo, op.indexOf(simbolo.getSimbolo()) + 1);
            op.remove(simbolo.getSimbolo());
            return;
        } else if (vlStr1.contains(SimboloEnum.PORCENTAGEM.getSimbolo())) {
            this.calcularPorcentagem(simbolo, op.indexOf(simbolo.getSimbolo()));
            op.remove(simbolo.getSimbolo());
            return;
        }

        BigDecimal value1 = new BigDecimal(numeros.get(op.indexOf(simbolo.getSimbolo())));
        BigDecimal value2 = new BigDecimal(numeros.get(op.indexOf(simbolo.getSimbolo()) + 1));

        String resultado = "";

        switch (simbolo) {
            case SOMAR:
                resultado = String.valueOf(calculadoraBasica.somar(value1, value2));
                break;
            case SUBTRAIR:
                resultado = String.valueOf(calculadoraBasica.subtrair(value1, value2));
                break;
            case MULTIPLICAR:
                resultado = String.valueOf(calculadoraBasica.multiplicar(value1, value2));
                break;
            case DIVIDIR:
                resultado = String.valueOf(calculadoraBasica.dividir(value1, value2));
                break;
            case ELEVAR:
                resultado = String.valueOf(calculadoraAvancado.elevar(value1, value2));
                break;
            default:
                break;
        }

        numeros.set(op.indexOf(simbolo.getSimbolo()), resultado);
        numeros.remove(op.indexOf(simbolo.getSimbolo()) + 1);

        op.remove(simbolo.getSimbolo());

    }

    private void calcularPorcentagem(SimboloEnum simbolo, int indexPorcentagem) {

        String porcentagemNum = numeros.get(indexPorcentagem);

        porcentagemNum = porcentagemNum.substring(0, porcentagemNum.length() - 1);

        BigDecimal cem = new BigDecimal("100.0");

        BigDecimal value1;

        try {
            value1 = new BigDecimal(numeros.get(indexPorcentagem));
        }catch (NumberFormatException e) {
            String str = numeros.get(indexPorcentagem -1);

            if (str.contains("%")){
                SimboloEnum simboloEnum = SimboloEnum.toEnum(op.indexOf(simbolo.getSimbolo()) -1);
                calcularPorcentagem(simboloEnum, indexPorcentagem -1);
            }
            value1 = new BigDecimal(numeros.get(indexPorcentagem -1));
        }

        BigDecimal value2 = new BigDecimal(porcentagemNum).divide(cem);
        String resultado;

        if (simbolo.equals(SimboloEnum.DIVIDIR))
            resultado = String.valueOf(calculadoraBasica.dividir(value1, value2));
        else
            resultado = String.valueOf(calculadoraBasica.multiplicar(value1, value2));

        if (simbolo.equals(SimboloEnum.SUBTRAIR))
            resultado = String.valueOf(calculadoraBasica.subtrair(value1, new BigDecimal(resultado)));
        else if (simbolo.equals(SimboloEnum.SOMAR))
            resultado = String.valueOf(calculadoraBasica.somar(value1, new BigDecimal(resultado)));

        numeros.set(indexPorcentagem -1, resultado);
        numeros.remove(indexPorcentagem);

    }
}
