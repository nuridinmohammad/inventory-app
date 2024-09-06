package com.multibahana.inventoryapp.utils;

import javax.swing.text.*;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyDocumentFilter extends DocumentFilter {

    private final NumberFormat currencyFormat;

    public CurrencyDocumentFilter() {
        this.currencyFormat = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        this.currencyFormat.setMaximumFractionDigits(0);  
        this.currencyFormat.setGroupingUsed(true); 
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

        updateText(fb, newText, attrs);
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + currentText.substring(offset + length);

        updateText(fb, newText, null);
    }

    private void updateText(FilterBypass fb, String text, AttributeSet attrs) throws BadLocationException {
        String cleanText = text.replaceAll("[^\\d]", "");

        try {
            if (cleanText.isEmpty()) {
                fb.remove(0, fb.getDocument().getLength());
                return;
            }

            long number = Long.parseLong(cleanText);

            String formattedText = currencyFormat.format(number);

            fb.remove(0, fb.getDocument().getLength());
            fb.insertString(0, formattedText, attrs);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
