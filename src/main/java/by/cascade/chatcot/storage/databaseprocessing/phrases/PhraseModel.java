package by.cascade.chatcot.storage.databaseprocessing.phrases;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class PhraseModel {
    private static final Logger LOGGER = LogManager.getLogger(PhraseModel.class);
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @JsonProperty("id")
    private int id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("phrase")
    private String phrase;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("owner")
    private int owner;

    public PhraseModel(int id, String type, String phrase, Date date, int owner) {
        this.id = id;
        this.type = type;
        this.phrase = phrase;
        this.date = date;
        this.owner = owner;
    }

    public PhraseModel(String type, String phrase, int owner) {
        this(0, type, phrase, new java.util.Date(), owner);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Date getDate() {
        return date;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhraseModel that = (PhraseModel) o;
        return id == that.id && (type != null ? type.equals(that.type) : that.type == null) && (phrase != null ? phrase.equals(that.phrase) : that.phrase == null) && (owner == that.owner);
    }

    @Override
    public int hashCode() {
        return  Integer.hashCode(id) +
                type.hashCode() +
                phrase.hashCode() +
                date.hashCode() +
                Integer.hashCode(owner);
    }

    @Override
    public String toString() {
        return id + " : " + type + " : " + phrase + " : " + date;
    }
}
