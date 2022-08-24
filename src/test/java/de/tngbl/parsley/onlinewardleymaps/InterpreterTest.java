package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.TestUtils;
import de.tngbl.parsley.wardleymapping.WardleyMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterTest {

    @Test
    public void shouldCreateMap() throws IOException {

        Parser parser = new Parser(TestUtils.TEA_SHOP_FILE);
        List<Expression> expressions = parser.parse();
        Interpreter interpreter = new Interpreter(expressions);

        WardleyMap map = interpreter.interpret();

        map.getMapElements().keySet().forEach(k -> System.out.println(map.getMapElements().get(k)));
        assertEquals(19, map.getMapElements().size());

    }

}
