package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileUtil {

  public static Map<String, String> getProperty() {
    var filePath = "src/test/resources/configs/config-env.properties";
    var properties = new Properties();
    try {
      var inputStream = Files.newInputStream(Path.of(filePath));
      properties.load(inputStream);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return properties.stringPropertyNames().stream()
        .collect(Collectors.toMap(Function.identity(), properties::getProperty));
  }
}
