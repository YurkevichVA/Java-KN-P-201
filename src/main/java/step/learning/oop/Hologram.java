package step.learning.oop;

public class Hologram extends Literature{
    public Hologram(String title) {
        super.setTitle( title );
    }
    @Override
    public String getCard() {
        return String.format(
                "Hologram: '%s' ",
                super.getTitle()
        );
    }
}
