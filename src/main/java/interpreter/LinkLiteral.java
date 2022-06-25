package interpreter;

public class LinkLiteral implements Token {

    public static boolean isLink(String s) {
        return s.trim().equals("->");
    }
}
