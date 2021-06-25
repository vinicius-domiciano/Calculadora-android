package com.example.textapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.textapp.enums.SimboloEnum;
import com.example.textapp.interfaces.CalcularEquacao;
import com.example.textapp.interfaces.impl.CalcularEquacaoImpl;
import com.example.textapp.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CalcularEquacao calcularEquacao = new CalcularEquacaoImpl();

    private String equacao = "";

    private List<Button> btnNumerorList = new ArrayList<>();
    private List<Button> btnSimboloList = new ArrayList<>();

    private TextView txtNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNumerorList.add(findViewById(R.id.button0));
        btnNumerorList.add(findViewById(R.id.button));
        btnNumerorList.add(findViewById(R.id.button2));
        btnNumerorList.add(findViewById(R.id.button3));
        btnNumerorList.add(findViewById(R.id.button4));
        btnNumerorList.add(findViewById(R.id.button5));
        btnNumerorList.add(findViewById(R.id.button6));
        btnNumerorList.add(findViewById(R.id.button7));
        btnNumerorList.add(findViewById(R.id.button8));
        btnNumerorList.add(findViewById(R.id.button9));

        btnSimboloList.add(findViewById(R.id.button_virgula));
        btnSimboloList.add(findViewById(R.id.button_porcentagem));
        btnSimboloList.add(findViewById(R.id.button_multiplicar));
        btnSimboloList.add(findViewById(R.id.button_dividir));
        btnSimboloList.add(findViewById(R.id.button_subtrair));
        btnSimboloList.add(findViewById(R.id.button_adicionar));
        btnSimboloList.add(findViewById(R.id.button_igual));
        btnSimboloList.add(findViewById(R.id.button_elevar));
        btnSimboloList.add(findViewById(R.id.button_deletar));
        btnSimboloList.add(findViewById(R.id.button_apagar_tudo));

        txtNumber = findViewById(R.id.txt_calculo);

        acionaButtonsNumeros();
        acionaButtonsSimbolos();
    }

    private void acionaButtonsNumeros() {
        int index = btnNumerorList.size();

        for (int i = 0; i < index; i++) {

            String valueBtn = String.valueOf(i);

            btnNumerorList.get(i).setOnClickListener(v -> {
                if (Boolean.FALSE.equals("%".equals(ultimoSimbolo()))) {
                    equacao += valueBtn;
                    addTextView(equacao);
                }
            });
        }
    }

    private void acionaButtonsSimbolos() {
        int index = btnSimboloList.size();

        for (int i = 0; i < index; i++) {

            String valueBtn = btnSimboloList.get(i).getText().toString();

            btnSimboloList.get(i).setOnClickListener(v -> {
                acaoSimbolo(SimboloEnum.toEnum(valueBtn));
                addTextView(equacao);
            });
        }

    }

    private void acaoSimbolo(SimboloEnum acao) {

        switch (acao) {
            case IGUAL:
                calcular();
                break;
            case LIMPAR:
                limparTudo();
                break;
            case REMOVER:
                deletarUltimoValor();
                break;
            case SOMAR:
                addSimbolo(SimboloEnum.SOMAR);
                break;
            case SUBTRAIR:
                addSimbolo(SimboloEnum.SUBTRAIR);
                break;
            case MULTIPLICAR:
                addSimbolo(SimboloEnum.MULTIPLICAR);
                break;
            case DIVIDIR:
                addSimbolo(SimboloEnum.DIVIDIR);
                break;
            case PORCENTAGEM:
                addSimbolo(SimboloEnum.PORCENTAGEM);
                break;
            case DECIMAL:
                addSimbolo(SimboloEnum.DECIMAL);
                break;
            case ELEVAR:
                addSimbolo(SimboloEnum.ELEVAR);
                break;
            default: System.out.println("Nenhuma ação selecionada");
                break;
        }

    }

    private void addSimbolo(SimboloEnum simbolo) {

        if (SimboloEnum.PORCENTAGEM.equals(simbolo) || SimboloEnum.ELEVAR.equals(simbolo)) {
            switch (ultimoSimbolo()) {
                case "%":
                case "^":
                    return;
                default:
                    break;
            }
        }

        if (equacao.length() <= 0 && !simbolo.equals(SimboloEnum.SUBTRAIR)) {
            return;
        } else if (simbolo.equals(SimboloEnum.DECIMAL) && existeDecimal(equacao)) {
            return;
        } else if (simbolo.equals(SimboloEnum.PORCENTAGEM) && existePorcentagem(equacao)) {
            return;
        } else if (Boolean.TRUE.equals(ultimoCaractereSimbolo()) && Boolean.FALSE.equals(ultimoCaracterePorcentagem())) {
            if (equacao.length() > 1)
                deletarUltimoValor();
            else
                return;
        } else if (simbolo.equals(SimboloEnum.DECIMAL) && SimboloEnum.ELEVAR.getSimbolo().equals(ultimoSimbolo())) {
            return;
        }

        equacao += simbolo.getSimbolo();

    }

    private boolean existeDecimal(String valor) {
        List<String> numeros = new ArrayList<>(Arrays.asList(valor.split("\\+|-|/|X|^")));

        int indiceUltimoNumero = numeros.size() - 1;

        if (indiceUltimoNumero < 0)
            return Boolean.FALSE;

        String ultimoNumero = numeros.get(indiceUltimoNumero);

        if (ultimoNumero.contains(SimboloEnum.PORCENTAGEM.getSimbolo()))
            return true;

        return ultimoNumero.contains(SimboloEnum.DECIMAL.getSimbolo());
    }

    private boolean existePorcentagem(String valor) {
        List<String> numeros = new ArrayList<>(Arrays.asList(valor.split("[+\\-/X]")));

        int indiceUltimoNumero = numeros.size() - 1;

        if (indiceUltimoNumero < 0)
            return Boolean.FALSE;

        String ultimoNumero = numeros.get(indiceUltimoNumero);

        if (numeros.size() == 1)
            return true;

        return ultimoNumero.contains(SimboloEnum.PORCENTAGEM.getSimbolo());
    }

    private boolean ultimoCaractereSimbolo() {
        int ultimoIndice = equacao.length() - 1;

        if (ultimoIndice < 0)
            return false;

        String ultimoCaracter = String.valueOf(equacao.charAt(ultimoIndice));

        try {
            SimboloEnum simboloEnum = SimboloEnum.toEnum(ultimoCaracter);

            if (simboloEnum == null)
                return Boolean.FALSE;

            return Boolean.TRUE;
        }catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    private String ultimoSimbolo() {
        List<String> op = Arrays.asList(equacao.replaceAll("([0-9])|\\.", "").split(""));
        return op.get(op.size() -1);
    }

    private boolean ultimoCaracterePorcentagem() {
        int ultimoIndice = equacao.length() - 1;

        if (ultimoIndice < 0)
            return false;

        String ultimoCaracter = String.valueOf(equacao.charAt(ultimoIndice));

        return SimboloEnum.PORCENTAGEM.getSimbolo().equals(ultimoCaracter);
    }

    private void deletarUltimoValor() {
        int ultimoIndice = equacao.length() - 1;

        if (ultimoIndice >= 0)
            equacao = equacao.substring(0, ultimoIndice);
    }

    private void limparTudo() {
        equacao = "";
    }

    private void addTextView(String value) {
        CharSequence charSequence = value.subSequence(0, value.length());
        txtNumber.setText(charSequence);
    }

    private void calcular() {
        System.out.println(equacao);

        String ultimoCaracter = String.valueOf(equacao.charAt(equacao.length() - 1));

        if (ultimoCaractereSimbolo() && !ultimoCaracter.equals(SimboloEnum.PORCENTAGEM.getSimbolo()))
            return;

        equacao = calcularEquacao.formatarEquacaoBrAPadrao(equacao);

        String resultadoStr = String.valueOf(calcularEquacao.calcularPorPEMDAS(equacao));
        BigDecimal resultado = new BigDecimal(resultadoStr);

        equacao = NumberUtils.formatNumberBR(resultado);
    }

}