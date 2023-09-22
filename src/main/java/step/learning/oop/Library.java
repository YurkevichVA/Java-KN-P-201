package step.learning.oop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Library {
    List<Literature> cards = new ArrayList<>();

    public void add(Literature literature) {
        this.cards.add(literature);
    }

    public void printAllCards() {
        for (Literature card: cards) {
            System.out.println(card.getCard());
        }
    }
}
