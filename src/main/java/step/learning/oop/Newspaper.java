package step.learning.oop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper extends Literature implements Periodic, Printable, Multiple{
    private Date date;
    private int count;
    private static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat cardDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public Newspaper(String title, Date date, int count) {
        super.setTitle((title));
        this.setDate(date);
        this.setCount(count);
    }
    public Newspaper(String title, String date, int count) throws ParseException {
        this(title, sqlDateFormat.parse(date), count);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String getCard() {
        return String.format(
                "Newspaper '%s' from %s",
                super.getTitle(),
                cardDateFormat.format(this.getDate())
        );
    }

    @Override
    public String getPeriod() {
        return "daily";
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
