package pe.edu.upc.center.platform.card.infrastructure.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthSerializer extends JsonSerializer<YearMonth> {
    @Override
    public void serialize(YearMonth date, JsonGenerator gen, SerializerProvider serializer) throws IOException {
        gen.writeString(date.format(DateTimeFormatter.ofPattern("yyyy-MM")));
    }
}
