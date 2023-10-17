package step.learning;

import com.google.inject.Guice;
import com.google.inject.Injector;
import step.learning.async.AsyncDemo;
import step.learning.async.TaskDemo;
import step.learning.async.TaskHW;
import step.learning.basics.BasicsDemo;
import step.learning.basics.FilesDemo;
import step.learning.basics.LongestStringTask;
import step.learning.ioc.ConfigModule;
import step.learning.ioc.IocDemo;
import step.learning.oop.OopDemo;

public class App
{
    public static void main( String[] args )
    {
        //new BasicsDemo().run();
        //new FilesDemo().run();
        //new LongestStringTask().run();
        //new OopDemo().run();
        //Injector injector = Guice.createInjector(new ConfigModule());
        //IocDemo iocDemo = injector.getInstance(IocDemo.class); // замість new IocDemo()
        //iocDemo.run() ;
        //Guice.createInjector(new ConfigModule()).getInstance(AsyncDemo.class).run();
        Guice.createInjector(new ConfigModule()).getInstance(TaskHW.class).run();
    }
}

/*
Вступ. Новий проєкт.
1. JRE (Java Runtime Environment - аналог .NET - платформа запуску)
2. JDK (Java Developer Kit - набір розробника - компілятор + бібліотеки) за тим самим посиланням або через інструменти IDE
3. IDE (JetBrains Ides / NetBeans (Apache) / Eclipse / VS Code)

Новий проєкт.
1. Архетип (система збірки / управління пакетами ~ NuGet)
    Maven / Gradle / Ant / Idea
    Назва проєкту - довільна
    Архетип - quickstart
    ! розкрити додаткові налаштування, вписати групу "step.learning"
    Вибрати JDK, за відсутності - завантажити
2. Конфігурація запуску - після створення проєкту маємо лише розпакований шаблон, потрібна конфігурація.
    меню Run - Edit Configuration - Add new - Application
    Вводимо назву кофігурації (довільна, App)
    Та вибираємо головний клас (з методом main)
3. Пробний запуск
 */

/*
Про Java
Парадигма - ООП
Покоління - 4
Запуск - трансьований, на базі платформи
Вихідний код - Юнікод, файли.java
Виконавчий код - проміжний, файл.class
Висока чутливість до організації:
    - назва файлу збігається з назвою класу (реєстрочутливо) ->
        один файл - один клас (public)
    - назва пакету відповідає назві директорії
 */