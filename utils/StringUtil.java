/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.utils;

/**
 *
 * @author mohammadnuridin
 */
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

}
