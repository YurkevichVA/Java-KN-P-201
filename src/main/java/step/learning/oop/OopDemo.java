package step.learning.oop;

public class OopDemo {
    public void run() {
        Book book = new Book("D. Knuth", "Art of programming");
        Book book2 = new Book("Іван Багряний", "Тигролови");
        Book book3 = new Book("Frank Herbert", "Dune");
        //System.out.println(book.getCard());

        Library library = new Library();
        library.add(book);
        library.add(book2);
        library.add(book3);
        library.printAllCards();
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