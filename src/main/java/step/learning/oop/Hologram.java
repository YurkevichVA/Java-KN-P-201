package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Hologram extends Literature{
    @Required
    private String type;
    private static List<String> requiredFieldsNames;
    private static List<String> getRequiredFieldsNames() {
        if(requiredFieldsNames == null) {
            Field[] fields = Hologram.class.getDeclaredFields();   // getFields();
            Field[] fields2 = Hologram.class.getSuperclass().getDeclaredFields();

            requiredFieldsNames =
                    Stream.concat(
                                    Arrays.stream(fields),
                                    Arrays.stream(fields2))
                            .filter( field -> field.isAnnotationPresent(Required.class) )
                            .map(Field::getName)
                            .collect(Collectors.toList());
        }
        return requiredFieldsNames;
    }
    public Hologram(String title, String type) {
        super.setTitle( title );
        this.setType(type);
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String getCard() {
        return String.format(
                "Hologram: '%s' type %s",
                super.getTitle(),
                this.getType()
        );
    }
    @FromJsonParser
    public static Hologram fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = getRequiredFieldsNames().toArray(new String[0]);

        for(String field: requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException("Missing required field: " + field, 0);
        }
        return new Hologram(jsonObject.get(requiredFields[1]).getAsString(), jsonObject.get(requiredFields[0]).getAsString());
    }
    @ParseChecker
    public static boolean isParsebleFromJson(JsonObject jsonObject) {
        for(String field: getRequiredFieldsNames()) {
            if(!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }
}
