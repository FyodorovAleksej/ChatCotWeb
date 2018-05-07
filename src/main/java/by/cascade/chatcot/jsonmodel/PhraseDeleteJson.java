package by.cascade.chatcot.jsonmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhraseDeleteJson {
    @JsonProperty("phraseItemId")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
