package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Serializable
public class Journal extends Literature implements Copyable, Periodic, Printable, Multiple{
    private int number;
    private int count;

    public Journal(String title, int number, int count) {
        super.setTitle((title));
        this.setNumber(number);
        this.setCount(count);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String getCard() {
        return String.format(
                "Journal '%s' № %d",
                super.getTitle(),
                this.getNumber()
        );
    }

    @Override
    public String getPeriod() {
        return "monthly";
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @FromJsonParser
    public static Journal fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = {"title", "number", "count"};
        for(String field: requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException("Missing required field: " + field, 0);
        }
        return new Journal(jsonObject.get(requiredFields[0]).getAsString(), jsonObject.get(requiredFields[1]).getAsInt(), jsonObject.get(requiredFields[2]).getAsInt() );
    }
    @ParseChecker
    public static boolean isParsebleFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "number", "count"};
        for(String field: requiredFields) {
            if(!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }
}
