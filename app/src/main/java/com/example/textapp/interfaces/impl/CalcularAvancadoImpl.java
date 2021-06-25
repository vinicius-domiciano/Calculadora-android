package com.example.textapp.interfaces.impl;

import com.example.textapp.interfaces.CalcularAvancado;
import com.example.textapp.utils.NumberUtils;

import java.math.BigDecimal;

public class CalcularAvancadoImpl implements CalcularAvancado {


    @Override
    public Object elevar(Object valor, Object numeroElevar) {
        if (Boolean.FALSE.equals(NumberUtils.isNumber(valor)) || Boolean.FALSE.equals(NumberUtils.isNumber(numeroElevar)))
            throw new RuntimeException("Os dois valores devem ser numeros");
        else if (numeroElevar instanceof Integer)
            throw new RuntimeException("Numero para elevar deve ser inteiro");

        BigDecimal vlOne = new BigDecimal(valor.toString());
        int vlTwo = Integer.parseInt(numeroElevar.toString());
        return vlOne.pow(vlTwo);
    }
}
