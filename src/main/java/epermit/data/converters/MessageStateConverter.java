package epermit.data.converters;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import epermit.common.CreatedMessageState;

@Converter(autoApply = true)
public class MessageStateConverter implements AttributeConverter<CreatedMessageState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CreatedMessageState state) {
        if (state == null) {
            return null;
        }
        return state.getCode();
    }

    @Override
    public CreatedMessageState convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(CreatedMessageState.values()).filter(c -> c.getCode().equals(code)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}

