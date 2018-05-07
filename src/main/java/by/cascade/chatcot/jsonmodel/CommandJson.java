package by.cascade.chatcot.jsonmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommandJson {
    @JsonProperty("phraseTextNew")
    private String newPhraseText;
    @JsonProperty("choiceType")
    private String newType;

    public String getNewPhraseText() {
        return newPhraseText;
    }

    public void setNewPhraseText(String newPhraseText) {
        this.newPhraseText = newPhraseText;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }
}
