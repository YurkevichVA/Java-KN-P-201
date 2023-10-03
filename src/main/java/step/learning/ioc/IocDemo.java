package step.learning.ioc;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.hash.Md5HashService;
import step.learning.ioc.services.random.RandomService;

public class IocDemo {
    private final HashService digestHashService;
    private final HashService dsaHashService;

    private final RandomService randomService;

    // @Inject
    // private HashService hashService;
    //@Inject
    //private Md5HashService md5HashService;
    //private final HashService hashService;
    @Inject // !!
    public IocDemo(
            @Named("Digest-Hash")HashService digestHashService,
            @Named("DSA-Hash")HashService dsaHashService,
            RandomService randomService) {
        this.digestHashService = digestHashService;
        this.dsaHashService = dsaHashService;
        this.randomService = randomService;
    }

    /* Іменовані залежності дозволяють розділяти декілька різних об'єктів однакового інтерфейсу (класу).
    Це може бути корисним для String (DbPrefix, ConnectionString, ConfigFilename, UploadDir).
    А також для гешів з різною розрядністю
     */

    public void run() {
        System.out.println("IoC Demo");
        System.out.println("SHA: " + digestHashService.hash("IoC Demo"));
        System.out.println("MD5: " + dsaHashService.hash("IoC Demo"));
        System.out.println(randomService.randomHex(10));
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