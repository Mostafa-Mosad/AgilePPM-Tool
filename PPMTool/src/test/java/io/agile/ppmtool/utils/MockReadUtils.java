package io.agile.ppmtool.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class MockReadUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Object readObjectFromJsonFile(String jsonFilePath, Class clazz) throws IOException {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(readObjectFromJsonFile(jsonFilePath), clazz);
    }

    public static String readObjectFromJsonFile(String jsonFilePath) throws IOException {
        File file = ResourceUtils.getFile(jsonFilePath);
        String json = null;
        BufferedReader br = Files.newBufferedReader(file.toPath());
        json = br.lines().collect(Collectors.joining("\n"));
        return json;
    }

    public static List readListFromJsonFile(String jsonFilePath, Class clazz) throws IOException {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(readObjectFromJsonFile(jsonFilePath), type);
    }

}
