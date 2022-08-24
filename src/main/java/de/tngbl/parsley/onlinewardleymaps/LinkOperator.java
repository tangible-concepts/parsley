package de.tngbl.parsley.onlinewardleymaps;

public class LinkOperator implements Token {

    public static final String SYMBOL = "->";

    public static boolean isLink(String s) {
        return s.trim().equals(SYMBOL);
    }
}
