package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Book extends Literature implements Copyable, Printable{
    @Required
    private String author;
    private static List<String> requiredFieldsNames;
    private static List<String> getRequiredFieldsNames() {
        if(requiredFieldsNames == null) {
            Field[] fields = Book.class.getDeclaredFields();   // getFields();
            Field[] fields2 = Book.class.getSuperclass().getDeclaredFields();

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
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Book(String author, String title) {
        this.setAuthor( author );
        super.setTitle( title );
    }
    @Override
    public String getCard() {
        return String.format(
                "Book: %s '%s' ",
                this.getAuthor(),
                super.getTitle()
        );
    }
    @FromJsonParser
    public static Book fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = getRequiredFieldsNames().toArray(new String[0]);

        for(String field: requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException("Missing required field: " + field, 0);
        }
        return new Book(jsonObject.get(requiredFields[0]).getAsString(), jsonObject.get(requiredFields[1]).getAsString() );
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
