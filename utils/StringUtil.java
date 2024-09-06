package com.multibahana.inventoryapp.utils;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtil {

    public static String truncateText(String text, int maxWords) {
        String[] words = text.split("\\s+");
        if (words.length <= maxWords) {
            return text;
        }
        StringBuilder truncated = new StringBuilder();
        for (int i = 0; i < maxWords; i++) {
            truncated.append(words[i]).append(" ");
        }
        truncated.append("...");
        return truncated.toString().trim();
    }

    public static String generateProductCode(String product, int number) {
        String productCode = Stream.of(product.split(" "))
                .map(word -> word.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());

        productCode += number;

        return productCode;
    }

    public static boolean isDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String formatAsRupiah(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        currencyFormat.setMaximumFractionDigits(0);
        currencyFormat.setGroupingUsed(true);

        String formattedAmount = currencyFormat.format(amount).replace("Rp", "").trim();
        return "Rp. " + formattedAmount;
    }
    
    public static String formatAsRupiahWithoutPre(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        currencyFormat.setMaximumFractionDigits(0);
        currencyFormat.setGroupingUsed(true);

        String formattedAmount = currencyFormat.format(amount).replace("Rp", "").trim();
        return formattedAmount;
    }

}
