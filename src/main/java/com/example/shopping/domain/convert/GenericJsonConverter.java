package com.example.shopping.domain.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.extern.slf4j.Slf4j;


@Convert
@Slf4j
public class GenericJsonConverter<T> extends AttributeConverter<T, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("fail to serialize as object into Json : {}", attribute, e);
            throw new BusinessException(FAIL_SERIALIZE_OBJECT_INTO_JSON);
        }
    }

    @Override
    public T convertToEntityAttribute(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<T>() {});
        } catch (IOException e) {
            log.error("fail to deserialize as Json into Object : {}", jsonStr, e);
            throw new BusinessException(FAIL_DESERIALIZE_JSON_INTO_OBJECT);
        }
    }

}
