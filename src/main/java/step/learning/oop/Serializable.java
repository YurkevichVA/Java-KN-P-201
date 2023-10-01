package step.learning.oop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // інформація доступна при запуску (включається у виконавчий код)
@Target(ElementType.TYPE) // елементи, що позначаються даною анотацією (тип - клас)
public @interface Serializable { // для позначення Літератури, яка включається у файл

}

/*
Анотації - різновиди інтерфейсів (та їх реалізації)
Як правило використовуються для метаданих (додаткові, супровідні дані, які не впливають на самі дані, але їх доповнюють і покращують можливості пошуку/групування/сорутвання)
Головна відмінність від звичайних інтерфейсів - ці дані можуть стосуватись не лише типів, а й їх компонент (полів, мотедів тощо)
 */
