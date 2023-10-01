package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

@Serializable
public class Book extends Literature implements Copyable, Printable{
    private String author;

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
        String[] requiredField = {"title", "author"};
        for(String field: requiredField) {
            if(!jsonObject.has(field)) throw new ParseException("Missing required field: " + field, 0);
        }
        return new Book(jsonObject.get(requiredField[1]).getAsString(), jsonObject.get(requiredField[0]).getAsString() );
    }

    @ParseChecker
    public static boolean isParsebleFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"author", "title"};
        for(String field: requiredFields) {
            if(!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }
}
