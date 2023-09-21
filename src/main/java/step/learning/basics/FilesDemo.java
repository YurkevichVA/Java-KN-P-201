package step.learning.basics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class FilesDemo {
    public void run3() {
        String filename = "test.txt";
        try( OutputStream writer = new FileOutputStream( filename )){
            writer.write("Hello, World!".getBytes());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try (FileWriter writer = new FileWriter(filename, true)){
            writer.write("\nNew Line");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        try( InputStream reader = new FileInputStream(filename) ) {
            int c;
            while( ( c = reader.read() ) != -1 ) { // -1 -- EOF
                sb.append((char) c);
            }
            System.out.println(sb.toString());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream(4096) ;

        byte[] buf = new byte[512];
        try(InputStream reader =    new BufferedInputStream(                     // Схема читання
                                    new FileInputStream( filename ) ) ) {        // рядкового контенту
            int cnt;                                                             // з можливістю мультібайт-кодування
            while( (cnt = reader.read(buf) ) > 0 ) {                             // У такому разі неможна
                byteBuilder.write(buf, 0, cnt);                              // читати по одному символу,
            }                                                                    // оскільки заздалегідь
            String content = new String(                                         // невідомий його розмір
                    byteBuilder.toByteArray(),                                   // Рішення - зчитати все як
                    StandardCharsets.UTF_8                                       // байти і потім увесь масив
            ) ;                                                                  // перетворити до рядка із
            System.out.println( content );                                       // зазначенням кодування.
        } catch (IOException e) {                                                //
            System.err.println(e.getMessage());                                  //
        }

        try ( InputStream reader = new FileInputStream( filename ) ;
              Scanner scanner = new Scanner(reader) ) {
            while( scanner.hasNext() ) {
                System.out.println( scanner.next() );
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner kbScanner = new Scanner(System.in);
        System.out.print("Your name: ");
        String name = kbScanner.next();
        System.out.printf("Hello, %s!%n", name);

        Random random = new Random();
        // Задача: сформувати рядок випадкової довжини з випадковими символами (коди 20-127)
        /*
        Д.З. Реалізувати алгоритм пошуку найдовшого рядка у файлі
        - згенерувати файл з рядками випадкової довжини
        - провести пошук
        - вивести повідомлення:
            найдовший рядок номер 4 з довжиною 121 символ: фждоафдівоажлофваждлфіоважлдфоіважд
        * при старті запитувати максимальну довжину рядків
         */
    }
    public void run2() {
        File dir = new File("./uploads");
        // задача: перевірити чи є у проєкті папка, якщо немає - створити
        if(dir.exists()){
            if( dir.isDirectory()) {
                System.out.printf("Directory '%s' already exists%n", dir.getName());
            }
            else {
                System.out.printf("'%s' already exists BUT IS NOT DIRECTORY%n", dir.getName());
            }
        }
        else {
            if(dir.mkdir()){
                System.out.printf("Directory '%s' created", dir.getName());
            }
            else {
                System.out.printf("Directory '%s' creation error%n", dir.getName());
            }
        }
        File file = new File("./uploads/whitelist.txt");
        if(file.exists()){
            if( file.isFile()) {
                System.out.printf("File '%s' already exists%n", file.getName());
            }
            else {
                System.out.printf("'%s' already exists BUT IS NOT FILE%n", file.getName());
            }
        }
        else {
            try {
                if(file.createNewFile()){
                    System.out.printf("File '%s' created", file.getName());
                }
                else {
                    System.out.printf("File '%s' creation error%n", file.getName());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());;
            }
        }
    }
    public void run1() {
        // Основа роботи з файлами - java.io.File
        File dir = new File("./");

        // створення new File НЕ впливає на файлову систему, це лише
        // програмний об'єкт, який відповідає на зазначений шлях
        if(dir.exists()) {
            System.out.println("Path exists");

            System.out.println(dir.getAbsolutePath());

            System.out.printf("Path is %s %n", dir.isDirectory() ? "directory" : "file");

            for(File file: dir.listFiles()) {
                Date yourDate = new Date(file.lastModified());
                DateFormat formatter =  new SimpleDateFormat("dd-MM-yyyy hh-MM-ss");
                String formattedDate = formatter.format(yourDate);
                System.out.printf("%s\t\t%s\t\t%s\t\t%s", file.isDirectory() ? "d----" : "-f---", formattedDate, file.length(), file.getName());
                System.out.println();
            }
        }
        else {
            System.out.println("Path does not exist");
        }
    }
}
/* Робота з файлами розглядається у двох аспектах
    - робота з файловою системою: створення, пошук, переміщення, видалення тощо
    - використання файлів для збереження/відновлення даних
Зберігання/читання даних з файлів відбувається через Stream
    - це некеровані ресурси, потрібне закриття
    - базові засоби Stream значно обмежені роботою з одним байтом чи їх масивом
    - часто вживається "обгортки", які спрощують роботу з даними, розбиваючи потоки байт на типи Java
        = FileWriter/FileReader - додає роботу з символами та строками
 */