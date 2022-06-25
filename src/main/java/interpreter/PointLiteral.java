package interpreter;

public class PointLiteral implements Token {

    private final float x;
    private final float y;

    public PointLiteral(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static boolean isPointLiteral(String s) {
        return (s.startsWith("["));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static Token getFromExpression(String rawExpression) {

        int begin = rawExpression.indexOf("[")+1;
        int end = rawExpression.substring(begin).indexOf("]")-1;

        String[] numbers = rawExpression.substring(begin, end).split(",");

        float x = Float.parseFloat(numbers[0].trim());
        float y = Float.parseFloat(numbers[1].trim());

        return new PointLiteral(x,y);
    }
}
