package step.learning.basics;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesDemo {
    public void run() {
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

 */