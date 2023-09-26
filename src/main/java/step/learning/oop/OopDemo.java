package step.learning.oop;

public class OopDemo {
    public void run() {
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