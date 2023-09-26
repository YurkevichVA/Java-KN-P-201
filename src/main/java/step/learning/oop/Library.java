package step.learning.oop;

import com.sun.org.apache.xpath.internal.operations.Mult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Literature> funds = new ArrayList<>();

    public void add(Literature literature) {
        this.funds.add(literature);
    }

    public void printAllCards() {
        for (Literature card: funds) {
            System.out.println(card.getCard());
        }
    }

    public void printCopyable() {
        for(Literature literature: funds) {
            if(this.isCopiable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }

    public void printNonCopyable() {
        for(Literature literature: funds) {
            if(! this.isCopiable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }

    public boolean isCopiable(Literature literature) {
        return literature instanceof Copyable;
    }

    public void printPeriodic() {
        for(Literature literature: funds) {
            if(this.isPeriodic(literature) ) {
                Periodic litAsPeriodic = (Periodic) literature;
                System.out.println(literature.getCard() + " " + litAsPeriodic.getPeriod());
            }
        }
    }
    public void printPeriodicDuck() {
        for(Literature literature: funds) {
            try {
                Method getPeriodMethod = literature.getClass().getDeclaredMethod("getPeriod");
                System.out.println(getPeriodMethod.invoke(literature) + " " + literature.getCard());
            }
            catch (Exception ignored) {

            }
        }
    }

    public void printNonPeriodic() {
        for(Literature literature: funds) {
            if(! this.isPeriodic(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }

    public boolean isPeriodic(Literature literature) {
        return literature instanceof Periodic;
    }

    public void printPrintable() {
        for(Literature literature: funds) {
            if(this.isPrintable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }
    public void printNonPrintable() {
        for(Literature literature: funds) {
            if(! this.isPrintable(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }

    public boolean isPrintable(Literature literature) {
        return literature instanceof Printable;
    }

    public void printMultiple() {
        for(Literature literature: funds) {
            if(this.isMultiple(literature) ) {
                Multiple litAsMult = (Multiple) literature;
                System.out.println(literature.getCard() + " " + litAsMult.getCount());
            }
        }
    }
    public void printSingle() {
        for(Literature literature: funds) {
            if(! this.isMultiple(literature) ) {
                System.out.println(literature.getCard());
            }
        }
    }

    public boolean isMultiple(Literature literature) {
        return literature instanceof Multiple;
    }
}
