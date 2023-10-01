package step.learning.ioc;

import com.google.inject.Inject;
import step.learning.ioc.services.hash.HashService;

public class IocDemo {
    @Inject
    private HashService hashService;
    public void run() {
        System.out.println("IoC Demo");
        System.out.println(hashService.hash("IoC Demo"));
    }
}

/*
IoC - Inversion of Control (інверсія управління)
Архітектурний патерн - шаблон побудови (організації) коду згідно з яким управління створенням об'єктів делегується окремому модулю, який часто називають інжектором або контейнером служб.

Class1 { dbContext = new() ... } => Class1 { @Inject DbContext ... }
Class2 { dbContext = new() ... } => Class2 { @Inject DbContext ... }

Через це IoC також називають DI (Dependency Injection !! не плутати з DIP Dependency Inversion Principle)

Наявність IoC змінює підходи до організації структури, у т.ч. структури запуску, поділяючи її на 2 етапи: налаштування служб (інжектора) та резолюція (Resolve)
об'єктів - побудови "дерева" залежностей і розв'язання його (які створювати першими, які від них залежать і т.д.)

Поширені системи IoC - Spring, Guice
На прикладі Guice:
- встановлюємо - знаходимо залежність або JAR, додаємо до проєкту
- оголошуємо клас який буде відповідати за налаштування інжектора (реєструвати служби), нащадок AbstractModule - ConfigModule
- створюємо сам інжектор та передаємо йому клас налаштувань (див. App::main)

 */