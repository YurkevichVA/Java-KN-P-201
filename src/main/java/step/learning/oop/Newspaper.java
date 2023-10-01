package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Newspaper extends Literature implements Periodic, Printable, Multiple {
    @Required
    private Date date;
    @Required
    private int count;
    private static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat cardDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static List<String> requiredFieldsNames;
    private static List<String> getRequiredFieldsNames() {
        if(requiredFieldsNames == null) {
            Field[] fields = Newspaper.class.getDeclaredFields();   // getFields();
            Field[] fields2 = Newspaper.class.getSuperclass().getDeclaredFields();

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
    public Newspaper(String title, Date date, int count) {
        super.setTitle((title));
        this.setDate(date);
        this.setCount(count);
    }
    public Newspaper(String title, String date, int count) throws ParseException {
        this(title, sqlDateFormat.parse(date), count);
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String getCard() {
        return String.format(
                "Newspaper '%s' from %s",
                super.getTitle(),
                cardDateFormat.format(this.getDate())
        );
    }
    @Override
    public String getPeriod() {
        return "daily";
    }
    @Override
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    @FromJsonParser
    public static Newspaper fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = getRequiredFieldsNames().toArray(new String[0]);

        for(String field: requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException("Missing required field: " + field, 0);
        }
        return new Newspaper(jsonObject.get(requiredFields[2]).getAsString(), jsonObject.get(requiredFields[0]).getAsString(), jsonObject.get(requiredFields[1]).getAsInt() );
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
