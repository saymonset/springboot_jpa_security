package com.apress.spring.utils;

/**
 * Created by simon on 22/08/18.
 */
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import       com.fasterxml.jackson.databind.JsonSerializer;
import       com.fasterxml.jackson.databind.SerializerProvider;
public class JsonDateSerializer extends JsonSerializer<Date>{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String formattedDate = dateFormat.format(date);
        gen.writeString(formattedDate);
    }
}
