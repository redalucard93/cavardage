package converter;

import java.time.*;
import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConverterLocalDate implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        Instant instant = date.atStartOfDay().
        atZone(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);

    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {

        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant,
                ZoneId.systemDefault())
                .toLocalDate();

    }
    }

