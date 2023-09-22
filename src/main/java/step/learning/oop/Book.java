package step.learning.oop;

public class Book extends Literature{
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(String author, String title) {
        this.setAuthor( author );
        super.setTitle( title );
    }

    @Override
    public String getCard() {
        return String.format(
                "Book: %s '%s' ",
                this.getAuthor(),
                super.getTitle()
        );
    }
}
