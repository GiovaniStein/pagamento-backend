package com.example.pagamento_backend.infrastructure.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Formater {

    public static String formaterCurrency(BigDecimal value) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.of("pt", "br"));
        return formatter.getNumberInstance().format(value);
    }

    public static void main(String[] args) {
       String s = formaterCurrency(new BigDecimal("265.13"));
        System.out.println(s);
    }

}
