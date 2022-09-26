package pl.com.kantoch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

public class CsvLoader {

    public Collection<String> getCsvContent(String filePath) {
        InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8))){
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
