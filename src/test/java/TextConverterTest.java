import org.junit.jupiter.api.Test;

import PDF.TextConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TextConverterTest {
    @Test
    public void testConvertPDFtoTxtTest() {
        try {
            String[] result = TextConverter.convertPDFtoTxt("src/test/resources/transcript.pdf");
            for (String line : result) {
                System.out.println(line);
            }
        } catch (Exception e) {}
    }
}
