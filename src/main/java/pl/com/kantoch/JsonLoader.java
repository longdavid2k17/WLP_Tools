package pl.com.kantoch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.stream.Collectors;

public class JsonLoader
{
    public String loadFile(String filePath) throws Exception {
        File file = new File(filePath);
        if(!file.exists()) return loadFileFromResource(filePath);
        Object object;
        InputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        object = (String)bufferedReader.lines().collect(Collectors.joining("\n"));
        return (String) object;
    }

    public String loadFileFromResource(String filePath) throws Exception {
        Object object;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if(inputStream==null) throw new Exception("InputStream instance is null.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        object = (String)bufferedReader.lines().collect(Collectors.joining("\n"));
        return (String) object;
    }

    public String convertIntoJsonFile(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
