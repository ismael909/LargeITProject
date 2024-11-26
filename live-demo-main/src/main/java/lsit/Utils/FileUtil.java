package lsit.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readFromFile(String filePath, Class<T> clazz) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        return objectMapper.readValue(file, clazz);
    }

    public static <T> void writeToFile(String filePath, T data) throws IOException {
        objectMapper.writeValue(new File(filePath), data);
    }
}
