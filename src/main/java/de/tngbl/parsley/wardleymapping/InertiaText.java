package de.tngbl.parsley.wardleymapping;

public final class InertiaText {

    private final String text;

    private InertiaText(String text) {
        this.text = text;
    }

    public static InertiaText of(String text) {
        return new InertiaText(text);
    }

    public String value() {
        return text;
    }

    @Override
    public String toString() {
        return "InertiaText{" +
                "text='" + text + '\'' +
                '}';
    }

}
