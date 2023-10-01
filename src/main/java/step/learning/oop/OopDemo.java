package step.learning.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Objects;

public class OopDemo {
    public void run() {
        Library library = new Library();
        try {
            library.load();
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }

        library.printAllCards();
    }
    public void run3() {
        // уявимо що ми не знаємо тип поки не парсимо рядок
        String str = "{\"author\": \"D. Knuth\", \"title\": \"Art of programming\"}";
        JsonObject literatureObject = JsonParser.parseString(str).getAsJsonObject();
        Literature literature = null;
        if(literatureObject.has("author") ) {
            literature = new Book(literatureObject.get("author").getAsString(), literatureObject.get("title").getAsString() );
        }
        else if(literatureObject.has("number")) {
            literature = new Journal(literatureObject.get("title").getAsString(), literatureObject.get("number").getAsInt(), literatureObject.get("count").getAsInt() );
        }
        else if(literatureObject.has("date")) {
            try {
                literature = new Newspaper(literatureObject.get("title").getAsString(), literatureObject.get("date").getAsString(), literatureObject.get("count").getAsInt() );
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(literature.getCard());
    }
    public void run1() {
        // JSON - засобами Gson
        Gson gson = new Gson();
        String str = "{\"author\": \"D. Knuth\", \"title\": \"Art of programming\"}";
        Book book = gson.fromJson(str, Book.class);
        System.out.println(book.getCard());
        // оптимізований рядок
        System.out.println(gson.toJson(book)); // {"author":"D. Knuth","title":"Art of programming"}
        // В оптимізованому режимі поля зі значеннями null взагалі не включаються до рядка
        book.setAuthor(null);
        System.out.println(gson.toJson(book));
        // Для налаштування серілізатора використовується GsonBuilder
        Gson gson2 = new GsonBuilder()
                .setPrettyPrinting() // додаткові відступи та розриви
                .serializeNulls() // Включати до json поля з null
                .create();

        System.out.println(gson2.toJson(book));

        try ( InputStream bookStream
                      = this.getClass().getClassLoader().getResourceAsStream("book.json");
              InputStreamReader bookReader = new InputStreamReader( Objects.requireNonNull( bookStream ) ) ) {
            book = gson.fromJson( bookReader, Book.class );
            System.out.println( book.getCard() );
        }
        catch (IOException e) { System.err.println(e.getMessage()); }
    }
    public void run2() {
        Library library = new Library();

        try {
            library.add(new Book("D. Knuth", "Art of programming"));
            library.add(new Book("Іван Багряний", "Тигролови"));
            library.add(new Book("Frank Herbert", "Dune"));

            library.add(new Newspaper("Daily Mail", "2023-09-25",7));
            library.add(new Newspaper("Washington Post", "2022-09-25", 3));

            library.add(new Journal("Scince Journal", 163, 5));
            library.add(new Journal("Amogus Spawning", 123, 3));

            library.add(new Hologram("Horizontal symmetric text"));

            library.save();
        }
        catch (Exception e) {
            System.err.println("Literature creation error: " + e.getMessage());
        }

        library.printAllCards();

        System.out.println("---------------- COPYABLE --------------");
        library.printCopyable();
        System.out.println("-------------- NON COPYABLE --------------");
        library.printNonCopyable();

        System.out.println("---------------- PERIODIC --------------");
        library.printPeriodic();
        System.out.println("-------------- NON PERIODIC --------------");
        library.printNonPeriodic();

        System.out.println("---------------- PERIODIC 2 --------------");
        library.printPeriodicDuck();

        System.out.println("---------------- PRINTABLE --------------");
        library.printPrintable();
        System.out.println("-------------- NON PRINTABLE --------------");
        library.printNonPrintable();

        System.out.println("---------------- MULTIPLE --------------");
        library.printMultiple();
        System.out.println("----------------- SINGLE --------------");
        library.printSingle();
    }
}
/*
Ресурси проєкту - папка "resources" (src/main/resources), файли з якої за замовчанням копіюються у збірку (target/classes).
Це гарантує наявність ресурсів у підсумковому (зібраному) проєкті.
getClassLoader(), викликаний на довільному типі з нашого проєкту дозволить визначити розміщення папки класів, а відтак і ресурсів.
 */
/*
Бібліотека
Моделюємо книгосховище (бібліотеку) у якому є каталог (перелік наявних видань)
Видання є різного типу: книги, газети, журнали тощо
Кожен тип має однакову картку у каталозі, у якій відзначається тип видання.

Абстрагування:
    Література - термін, що поєднує реальні сутності (книги, газети, журнали тощо).
    Оскільки довільне видання повинно формувати картку для каталога, у цей класс (Література) має бути включений метод getCard(),
    але оскільки до нього входить відомість про тип (який відомий тільки у конкретному об'єкті), це метод може бути тільки абстрактним.
    Чи є у всіх "літератур" щось спільне (поле) ? Так, це назва. Відповідно, її бажано закласти на рівень абстрактного класу.
 */

/*
ООП - об'єктно-орієнтована парадигма програмування
Програма - управління об'єктами та їх взаємодією
Програмування - налаштування об'єктів та зв'язків
Види зв'язків:
    - спадкування
    - асоціація
    - композиція
    - агрегація
    - залежність
 */

/*
Робота з пакетами. JSON серіалізація.
Бібліотеки класів (аналог DLL) у Java постачаються як .JAR файли
Для використання їх можливостей необхідно додавати ці файли до збірки
Альтернатива - автоматизоване управління пакетами IDE з їх підключенням за допомогою декларацій у pom.xml (секція dependencies)
Maven має репозиторій - онлайн бібліотеку залежностей, завантаження з якої відбувається засобами IDE.
 */