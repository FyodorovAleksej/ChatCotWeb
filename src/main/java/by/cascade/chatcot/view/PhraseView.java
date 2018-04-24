package by.cascade.chatcot.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import static by.cascade.chatcot.storage.databaseprocessing.phrases.PhraseModel.DATE_FORMAT;

public class PhraseView {
    private String phrase;
    private String type;
    private Date date;
    private String owner;
    private int id;

    public PhraseView(int id, String phrase, String type, Date date, String owner) {
        this.id = id;
        this.phrase = phrase;
        this.type = type;
        this.date = date;
        this.owner = owner;
    }

    public String getId() {return Integer.toString(id);}

    public String getPhrase() {
        return phrase;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    public String getOwner() {
        return owner;
    }
}
