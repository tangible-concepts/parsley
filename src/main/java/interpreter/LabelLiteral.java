package interpreter;

public class LabelLiteral extends PointLiteral {

    public LabelLiteral(float x, float y) {
        super(x, y);
    }
    public static Token getFromExpression(String rawExpression) {

        int labelPosition = rawExpression.indexOf("label");
        PointLiteral point = (PointLiteral) PointLiteral.getFromExpression(rawExpression.substring(labelPosition));

        return new LabelLiteral(point.getX(),point.getY());
    }
}
