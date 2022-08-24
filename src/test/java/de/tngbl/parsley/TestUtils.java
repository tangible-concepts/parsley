package de.tngbl.parsley;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestUtils {


    public static final String TEA_SHOP = "/tea-shop";
    public static final String LINKS = "/links";

    public static final File TEA_SHOP_FILE = new File(Objects.requireNonNull(TestUtils.class.getResource(TEA_SHOP)).getFile());
    public static final String TEA_SHOP_FILE_LOCATION = new File(Objects.requireNonNull(TestUtils.class.getResource(TEA_SHOP)).getFile()).getAbsolutePath();


    public static List<String> getTeaShopTestData() {
        return getExampleTestData(TEA_SHOP);
    }

    public static List<String> getLinksTestData() {
        return getExampleTestData(LINKS);
    }

    static private List<String> getExampleTestData(String data) {

        File file = new File(Objects.requireNonNull(TestUtils.class.getResource(data)).getFile());
        try {
            return Files.lines(file.toPath()).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}