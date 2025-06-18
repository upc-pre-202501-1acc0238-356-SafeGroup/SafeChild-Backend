package pe.edu.upc.center.platform.card.infrastructure.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthDeserializer extends JsonDeserializer<YearMonth> {
    @Override
    public YearMonth deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return YearMonth.parse(p.getText(), DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
