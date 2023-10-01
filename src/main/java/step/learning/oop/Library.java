package step.learning.oop;

import com.google.gson.*;
import com.sun.org.apache.xpath.internal.operations.Mult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Library {
    private final List<Literature> funds = new ArrayList<>();

    public List<Literature> getFunds() {
        return funds;
    }

    public void add(Literature literature) {
        this.funds.add(literature);
    }

    public void printAllCards() {
        for (Literature card: funds) {
            System.out.println(card.getCard());
        }
    }

    public void printCopyable() {
        for(Literature literature: funds) {
            if(this.isCopiable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }
    public void printNonCopyable() {
        for(Literature literature: funds) {
            if(! this.isCopiable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }
    public boolean isCopiable(Literature literature) {
        return literature instanceof Copyable;
    }

    public void printPeriodic() {
        for(Literature literature: funds) {
            if(this.isPeriodic(literature) ) {
                Periodic litAsPeriodic = (Periodic) literature;
                System.out.println(literature.getCard() + " " + litAsPeriodic.getPeriod());
            }
        }
    }
    public void printPeriodicDuck() {
        for(Literature literature: funds) {
            try {
                Method getPeriodMethod = literature.getClass().getDeclaredMethod("getPeriod");
                System.out.println(getPeriodMethod.invoke(literature) + " " + literature.getCard());
            }
            catch (Exception ignored) {

            }
        }
    }
    public void printNonPeriodic() {
        for(Literature literature: funds) {
            if(! this.isPeriodic(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }
    public boolean isPeriodic(Literature literature) {
        return literature instanceof Periodic;
    }

    public void printPrintable() {
        for(Literature literature: funds) {
            if(this.isPrintable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }
    public void printNonPrintable() {
        for(Literature literature: funds) {
            if(! this.isPrintable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }
    public boolean isPrintable(Literature literature) {
        return literature instanceof Printable;
    }

    public void printMultiple() {
        for(Literature literature: funds) {
            if(this.isMultiple(literature) ) {
                Multiple litAsMult = (Multiple) literature;
                System.out.println(literature.getCard() + " " + litAsMult.getCount());
            }
        }
    }
    public void printSingle() {
        for(Literature literature: funds) {
            if(! this.isMultiple(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }
    public boolean isMultiple(Literature literature) {
        return literature instanceof Multiple;
    }

    public void save() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        FileWriter writer = new FileWriter("./src/main/resources/library.json");
        writer.write(gson.toJson(this.getFunds()));
        writer.close();
    }
    public void load() throws RuntimeException {
        try( InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(
                        this.getClass().getClassLoader().getResourceAsStream("library.json" ) ) ) ) {
            this.funds.clear();
            for(JsonElement item: JsonParser.parseReader( reader ).getAsJsonArray() ) {
                this.funds.add(this.fromJson(item.getAsJsonObject() ) );
            }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        catch (NullPointerException ignored) {
            throw new RuntimeException("Resource not found");
        }
    }

    private Literature fromJson(JsonObject jsonObject) throws ParseException {
        Class<?>[] literatures = {Book.class, Journal.class, Newspaper.class, Hologram.class };
        try {
            for(Class<?> literature: literatures) {
                Method isParsebleFromJson = literature.getMethod("isParsebleFromJson", JsonObject.class);
                isParsebleFromJson.setAccessible(true);
                boolean res = (boolean) isParsebleFromJson.invoke(null, jsonObject);
                if (res) {
                    Method fromJson = literature.getMethod("fromJson", JsonObject.class);
                    fromJson.setAccessible(true);
                    return (Literature) fromJson.invoke(null, jsonObject);
                }
            }
        } catch (Exception e) {
            throw new ParseException("Reflection error: " + e.getMessage(), 0);
        }

//        if(Book.isParsebleFromJson(jsonObject) ) {
//            return Book.fromJson(jsonObject);
//        }
//
//        if(Journal.isParsebleFromJson(jsonObject)) {
//            return Journal.fromJson(jsonObject);
//        }
//
//        if(Newspaper.isParsebleFromJson(jsonObject)) {
//            return Newspaper.fromJson(jsonObject);
//        }
//
          throw new ParseException("Literature type unrecognized", 0);
    }
}