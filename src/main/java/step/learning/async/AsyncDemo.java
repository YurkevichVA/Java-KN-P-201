package step.learning.async;

import java.util.Locale;
import java.util.Random;

public class AsyncDemo {
    public void run() {
        str = "";
        activeThreadsCountForString = 10;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++){
            threads[i] = new Thread(new StringMaker());
            threads[i].start();
        }
    }
    private String str;
    private final Object stringLocker = new Object();
    private int activeThreadsCountForString ;
    class StringMaker implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            String localString;

            synchronized (stringLocker) {
                activeThreadsCountForString--;
                localString = str = str + activeThreadsCountForString;
                if (activeThreadsCount == 0) {
                    System.out.printf("Result string: %s%n", str);
                } else {
                    System.out.printf("Current str: %s%n", str);
                }

            }
        }
    }


    public void run1() {
        System.out.println( "Async demo" ) ;
        //multiThreadDemo();
        //Hw();
        sum = 100.0;
        int months = 12;
        activeThreadsCount = months;
        Thread[] threads = new Thread[months];
        for (int i = 0; i < months; i++){
            threads[i] = new Thread(new MonthRate(i + 1));
            threads[i].start();
        }
        // визначення підсумку роботи всіх потоків. Варіант 1) - чекаємо всі
        /*
        try {
            for (int i = 0; i < months; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf(Locale.US, "----------------%ntotal: %.2f%n", sum);
        */
    }
    private double sum;
    private final Object sumLocker = new Object();
    private final Object atcLocker = new Object();
    private int activeThreadsCount ; // кількість потоків, які ще не завершились
    class MonthRate implements Runnable{
        public MonthRate(int month) {
            this.month = month;
        }

        private int month;
        @Override
        public void run() {
            try {
                Thread.sleep(1000); // иммитация запроса
            } catch (InterruptedException ignored) {}
            double p = 0.1; // иммитация результата запроса
            double localSum;
            // "добавляем свой результат к общей сумме
            synchronized (sumLocker) { // "закрити" SumLocker
                localSum = sum = sum * (1 + p);

            }
            System.out.printf(Locale.US,"month: %02d, percent: %.2f, sum: %.2f%n", month, p, localSum);
            synchronized (atcLocker) {
                activeThreadsCount--;
                if (activeThreadsCount == 0) {
                    System.out.printf(Locale.US, "----------------%ntotal: %.2f%n", sum);
                }
            }
        }
    }


    private void multiThreadDemo() {
        Thread thread = new Thread( // об'єкт Thread відповідає за системний ресурс - потік
                new Runnable() {        // Сильна ООП не дозволяє передавати лише метод, а
                    @Override           // потребує функціональний інтерфейс - інтерфейс з одним методом
                    public void run() { // Java дозволяє імплементувати інтерфейси у точці виклику
                        try {
                            System.out.println("Thread starts");
                            Thread.sleep(2000);
                            System.out.println("Thread finishes");
                        } catch (InterruptedException e) {
                            System.err.println("Sleeping broken " + e.getMessage());
                        }
                    }
                }
        ); // Створення об'єкту НЕ запускає його активність
        // Запуск можливий у двох режимах: синхронному (run) та асинхронному (start)
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread joining interrupted " + e.getMessage());
        }
        new Thread(()->{
            System.out.println("Thread 2 starts");
        }).start();
        new Thread(this::methodForThread).start();
        System.out.println("multiThreadDemo() finishes");
    }
    private void methodForThread() {
        System.out.println("methodForThread starts");
    }
    private void mutlriThredHw() {
        Thread first = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("1 start");
                            Thread.sleep(5000);
                            System.out.println("1 finish");
                        } catch (InterruptedException e) {
                            System.err.println("Sleeping broken " + e.getMessage());
                        }
                    }
                }
        );
        Thread second = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Thread third = new Thread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            System.out.println("3 start");
                                            Thread.sleep(1000);
                                            System.out.println("3 finish");
                                        } catch (InterruptedException e) {
                                            System.err.println("Sleeping broken " + e.getMessage());
                                        }
                                    }
                                }
                        );
                        try {
                            System.out.println("2 start");
                            Thread.sleep(1000);
                            System.out.println("2 finish");
                            third.start();
                        } catch (InterruptedException e) {
                            System.err.println("Sleeping broken " + e.getMessage());
                        }
                    }
                }
        );


        first.start();
        second.start();
    }
}
/*
Асинхронне програмування
Синхронність (у програмування) - послідовне, один-за-одним виконнання інструкцій
Асинхронність - будь яке відхилення від синхронності

Способи реалізації асинхронності:
 - багатозадачність - рівень мови програмування, використання засобів мови/платформи
 - багатопоточність - рівень операційної системи у межах одного процесу (процесора)
 - багатопроцесність - рівень ОС та процесора
 - мережні технологої (grid-, network-)
 */
/*
Синхронізація - утворення ситуації гарантовано послідовного виконання певного коду
(без можливості кілької активностей одночасно)
Іншими словами це утворення "транзакції" - набору команд. які виконуються як єдине ціле.
Зупинка (павза) потоків - складна системна задача, для її вирішення використовуються "сигнальні" об'єкти, серед яких
критична секція, м'ютекси, семафори тощо. У мовах типу Java (та C#) усі reference-типи мають у своєму складі критичні
секції.
Поширеня практика - використання у якості синхро-об'єктів вже наявні
synchronized ( list ) {
    list.add( item ) ;
}
Але це небезпечно, оскільки може пеерепризначити сам об'єкт, наприклад
synchronized ( str ) {      блокується один str
    str += "Hello"          створюється новий str
}                           розблоковується незаблокований (новий) str, старий - deadlock
Тому рекомендується синхронізуватись тільки за константними об'єктами
 */

