package com.example.textapp.interfaces.impl;

import com.example.textapp.interfaces.CalcularOperacaoBasica;
import com.example.textapp.utils.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcularOperacaoBasicaImpl implements CalcularOperacaoBasica {

    @Override
    public Object somar(Object value, Object value2) {
        if (Boolean.FALSE.equals(NumberUtils.isNumber(value)) || Boolean.FALSE.equals(NumberUtils.isNumber(value2)))
            throw new RuntimeException("Os dois valores devem ser numeros");

        BigDecimal vlOne = new BigDecimal(value.toString());
        BigDecimal vlTwo = new BigDecimal(value2.toString());
        return vlOne.add(vlTwo);
    }

    @Override
    public Object subtrair(Object value, Object value2) {
        if (Boolean.FALSE.equals(NumberUtils.isNumber(value)) || Boolean.FALSE.equals(NumberUtils.isNumber(value2)))
            throw new RuntimeException("Os dois valores devem ser numeros");

        BigDecimal vlOne = new BigDecimal(value.toString());
        BigDecimal vlTwo = new BigDecimal(value2.toString());
        return vlOne.subtract(vlTwo);
    }

    @Override
    public Object dividir(Object dividendo, Object divisor) {
        if (Boolean.FALSE.equals(NumberUtils.isNumber(dividendo)) || Boolean.FALSE.equals(NumberUtils.isNumber(divisor)))
            throw new RuntimeException("Os dois valores devem ser numeros");

        BigDecimal vlOne = new BigDecimal(dividendo.toString());
        BigDecimal vlTwo = new BigDecimal(divisor.toString());
        return vlOne.divide(vlTwo, 24, RoundingMode.HALF_EVEN);
    }

    @Override
    public Object multiplicar(Object value, Object value2) {
        if (Boolean.FALSE.equals(NumberUtils.isNumber(value)) || Boolean.FALSE.equals(NumberUtils.isNumber(value2)))
            throw new RuntimeException("Os dois valores devem ser numeros");

        BigDecimal vlOne = new BigDecimal(value.toString());
        BigDecimal vlTwo = new BigDecimal(value2.toString());
        return vlOne.multiply(vlTwo);
    }

}
