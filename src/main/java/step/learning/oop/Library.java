package step.learning.oop;

import com.google.gson.*;
import com.sun.org.apache.xpath.internal.operations.Mult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
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

    private List<Literature> getSerializableFunds() {
        List<Literature> serializebleFunds = new ArrayList<>();
        for(Literature literature: getFunds()) {
            // перевіряємо наявність анотації Serializable у класа
            if(literature.getClass().isAnnotationPresent(Serializable.class)) {
                serializebleFunds.add(literature);
            }
        }
        return serializebleFunds;
    }
    public void save() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
        FileWriter writer = new FileWriter("./src/main/resources/library.json");
        writer.write(gson.toJson(this.getSerializableFunds() ) );
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

    private List<Class<?>> getSerializableClasses() {
        List<Class<?>> literatures = new LinkedList<>();
        // знайти усі класи з анотацією Serializeble
        // Рішення - сканування папки зі зкомпильованими класами, звернення до них, перевірка анотації.
        // Вибір папки для сканування - або регулюється політикою проєкту, або робиться рекурсивно по всіх підпапках проєкту.
        // Будемо вважати, що для нашого проєкту сканується лише папка "оор"
        String className = Literature.class.getName();
        // видаляємо ім'я самого класу, залишаємо лише пакет
        String packageName = className.substring(0, className.lastIndexOf('.') );
        // Використовуємо той факт, що пакети однозначно пов'язані з файловою системою
        // для формування файлового шляху замінюємо точки на слеші
        String packagePath = packageName.replace(".", "/");
        // Звертаємось до даного шляху як до ресурсу, та визначаємо його реальний шлях
        String absolutePath = Literature.class.getClassLoader().getResource(packagePath).getPath();
        // Отримуємо перелік визначеної директорії (див. тему "файли")
        File[] files = new File(absolutePath).listFiles();
        if(files == null ){
            throw new RuntimeException("Class path inaccessible");
        }
        for(File file: files) {
            if(file.isFile()) {
                // далі спираємось на те що імена файлів = імена класів, лише файли закінчуються на .class
                String filename = file.getName();
                if(filename.endsWith(".class")) {
                    String fileClassName = filename.substring(0, filename.lastIndexOf('.'));
                    try {
                        Class<?> fileClass = Class.forName(packageName + "." + fileClassName);
                        if(fileClass.isAnnotationPresent(Serializable.class)) {
                            literatures.add(fileClass);
                        }
                    } catch (ClassNotFoundException ignored) {
                        continue;
                    }
                }
            }
            else if(file.isDirectory()) { // за потреби скануємо вкладені папки
                continue;
            }
        }
        return literatures;
    }
    private Literature fromJson(JsonObject jsonObject) throws ParseException {
        List<Class<?>> literatures = this.getSerializableClasses();
        try {
            for(Class<?> literature: literatures) {
                // Method isParsebleFromJson = literature.getMethod("isParsebleFromJson", JsonObject.class);

                Method isParsebleFromJson = null;

                for(Method method: literature.getDeclaredMethods()) {
                    if(method.isAnnotationPresent(ParseChecker.class)) {
                        if(isParsebleFromJson != null) {
                            throw new ParseException("Multiple ParseChecker annotations", 0);
                        }
                        isParsebleFromJson = method;
                    }
                }
                if(isParsebleFromJson == null) {
                    continue;
                }

                isParsebleFromJson.setAccessible(true);
                boolean res = (boolean) isParsebleFromJson.invoke(null, jsonObject);
                if (res) {
                    // Method fromJson = literature.getMethod("fromJson", JsonObject.class);
                    Method parseFromJson = null;
                    for(Method method : literature.getDeclaredMethods()) {
                        if(method.isAnnotationPresent(FromJsonParser.class)) {
                            if(parseFromJson != null) {
                                throw new ParseException("Multiple FromJsonParser annotations", 0);
                            }
                            parseFromJson = method;
                        }
                    }
                    parseFromJson.setAccessible(true);
                    return (Literature) parseFromJson.invoke(null, jsonObject);
                }
            }
        } catch (Exception e) {
            throw new ParseException("Reflection error: " + e.getMessage(), 0);
        }
        //if(Book.isParsebleFromJson(jsonObject) ) {
        //return Book.fromJson(jsonObject);
        //}
        //        //if(Journal.isParsebleFromJson(jsonObject)) {
        //return Journal.fromJson(jsonObject);
        //}
        //        //if(Newspaper.isParsebleFromJson(jsonObject)) {
        //return Newspaper.fromJson(jsonObject);
        //}
        //
        throw new ParseException("Literature type unrecognized", 0);
    }
}