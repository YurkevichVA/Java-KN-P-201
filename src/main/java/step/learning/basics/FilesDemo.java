package step.learning.basics;

import java.io.File;

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

            for(String filename: dir.list()) {
                System.out.println(filename);
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