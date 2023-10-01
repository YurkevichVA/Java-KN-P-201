package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

public class Hologram extends Literature{
    public Hologram(String title) {
        super.setTitle( title );
    }
    @Override
    public String getCard() {
        return String.format(
                "Hologram: '%s' ",
                super.getTitle()
        );
    }

    public static Hologram fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = {"title"};
        for(String field: requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException("Missing required field: " + field, 0);
        }
        return new Hologram(jsonObject.get(requiredFields[0]).getAsString());
    }
    public static boolean isParsebleFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title"};
        for(String field: requiredFields) {
            if(!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }
}
