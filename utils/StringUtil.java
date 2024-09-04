package com.multibahana.inventoryapp.utils;

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

}
