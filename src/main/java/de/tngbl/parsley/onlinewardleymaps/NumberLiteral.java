package de.tngbl.parsley.onlinewardleymaps;

public class NumberLiteral implements Token {

    private final float number;

    public NumberLiteral(float number) {
        this.number = number;
    }

    public static boolean isNumberLiteral(String s) {

         return s.matches("(0|1)\\.[0-9]+");
    }

    public float getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "NumberLiteral{" +
                "number=" + number +
                '}';
    }
}
