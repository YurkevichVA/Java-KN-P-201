package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

@Serializable
public class Hologram extends Literature{
    private String type;
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
        String[] requiredFields = {"title", "type"};
        for(String field: requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException("Missing required field: " + field, 0);
        }
        return new Hologram(jsonObject.get(requiredFields[0]).getAsString(), jsonObject.get(requiredFields[1]).getAsString());
    }
    @ParseChecker
    public static boolean isParsebleFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "type"};
        for(String field: requiredFields) {
            if(!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }
}
