package com.distrib.server.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {

    private static ObjectMapper objMapper = defaulObjectMapper();

    private static ObjectMapper defaulObjectMapper() {
        ObjectMapper obj = new ObjectMapper();
        obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return obj;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return objMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object obj) {
        return objMapper.valueToTree(obj);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objWriter = objMapper.writer();
        if (pretty)
            objWriter = objMapper.writer().with(SerializationFeature.INDENT_OUTPUT);
        return objWriter.writeValueAsString(o);
    }

}
