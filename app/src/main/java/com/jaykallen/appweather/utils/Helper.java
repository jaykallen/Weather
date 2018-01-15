package com.jaykallen.appweather.utils;
// Created by Jay Kallen on 11/5/2017. 

public class Helper {

    public static String calcK2F (String kelvin) {
        // Calculation Kelvin to Fahrenheit.  Example formula: 300K × 9/5 - 459.67 = 80.33 °F
        double k = Double.parseDouble(kelvin);
        double f = (k * (9.0 / 5.0)) - 459.67;
        return String.format ("%.0f", f) + "";
    }

}
